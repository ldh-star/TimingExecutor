package com.liangguo.timingexecutor.core

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.lang.ref.WeakReference
import java.util.concurrent.Executors


/**
 * @author ldh
 * 时间: 2022/2/12 14:15
 * 邮箱: 2637614077@qq.com
 */
internal object TaskManager {

    /**
     * 使用Handler来切换到主线程，协程也可以
     * 这个工具可以单独打包成一个模块，所以就不引入协程了
     */
    private val mHandler = Handler(Looper.getMainLooper())

    /**
     * 这个工作线程会一直存在，但执不执行是根据running这个参数来决定的
     */
    private val mTimerExecutor = Executors.newSingleThreadExecutor()

    /**
     * 弱引用的任务池
     */
    private val mWeakReferenceTaskMap = WeakRefTaskMap()

    /**
     * 是否正在运行中
     */
    private var mRunning = false
        set(value) = run {
            synchronized(mRunning) {
                if (value != field) {
                    if (value) {
                        //这种情况是之前没有在运行中，并且现在才要来启动
                        startRunning()
                    }
                }
                field = value
            }
        }

    /**
     * 添加任务进来，任务已存在则会更新，不存在则会创建
     * @param delay 该任务持续的时间
     * @param id 该任务的id，唯一标识
     * @param mainThread 是否在主线程中执行
     * @param exec 结束时要执行的任务
     */
    fun putTask(
        delay: Long,
        id: Any,
        mainThread: Boolean,
        exec: () -> Unit
    ) {
        mWeakReferenceTaskMap.findTaskById(id)?.overrideParams(delay, mainThread, exec) ?: WeakRefTask(
            WeakReference(id),
            System.currentTimeMillis() + delay,
            mainThread,
            WeakReference(exec)
        ).also { newInstance ->
            synchronized(mWeakReferenceTaskMap) {
                mWeakReferenceTaskMap[newInstance.id] = newInstance
            }
        }
        mRunning = true
    }

    /**
     * 取消任务，会在两种map中都查找，如果有就删除。
     */
    fun cancelTask(id: Any) {
        mWeakReferenceTaskMap.removeTaskById(id)
    }

    /**
     * 开始启动检查任务
     */
    private fun startRunning() {
        mTimerExecutor.execute {
            while (mWeakReferenceTaskMap.isNotEmpty()) {
                Thread.sleep(TimingExecutorConfig.checkIntervalMills)
                mWeakReferenceTaskMap.clearNullTask()
                executeWeakRfTasks(mWeakReferenceTaskMap.shouldExecuteWeakRefTasks)
            }
            //若任务执行完了，那就把running设为false
            mRunning = false
        }
    }

    /**
     * 执行这些任务并且完成后移除掉他们。
     */
    private fun executeWeakRfTasks(tasks: MutableList<WeakRefTask>) {
        tasks.forEach {
            if (it.mainThread) {
                mHandler.post {
                    it.exec.get()?.invoke()
                }
            } else {
                it.exec.get()?.invoke()
            }
            mWeakReferenceTaskMap.removeTask(it)
        }
    }

}

