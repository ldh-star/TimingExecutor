package com.liangguo.timingexecutor.core

import android.os.Looper
import java.lang.ref.WeakReference


/**
 * @author ldh
 * 时间: 2022/3/10 11:11
 * 邮箱: 2637614077@qq.com
 */
/**
 * 弱引用持有id和执行函数的任务池。
 */
internal typealias WeakRefTaskMap = HashMap<WeakReference<out Any>, WeakRefTask>

/**
 * 从弱引用池中通过id查找弱引用任务。
 */
internal fun WeakRefTaskMap.findTaskById(id: Any): WeakRefTask? {
    forEach {
        if (it.key.get() == id)
            return it.value
    }
    return null
}

/**
 * 在弱引用任务中删除指定id的任务。
 * @return 是否删除成功。
 */
internal fun WeakRefTaskMap.removeTaskById(id: Any) {
    filterKeys { it.get() == id }.forEach {
        removeTask(it.value)
    }
}

internal fun WeakRefTaskMap.removeTask(task: WeakRefTask) = synchronized(this) {
    remove(task.id)
}

/**
 * 清除这个表里所有弱引用为null的任务。
 */
internal fun WeakRefTaskMap.clearNullTask() = synchronized(this) {
    filterKeys { it.get() == null }.forEach {
        removeTask(it.value)
    }
}

/**
 * 检查有哪些任务应该执行了，并返回这些应该执行的任务。
 */
internal val WeakRefTaskMap.shouldExecuteWeakRefTasks: MutableList<WeakRefTask>
    get() = mutableListOf<WeakRefTask>().also { list ->
        this.forEach {
            if (System.currentTimeMillis() > it.value.finishTime) {
                list.add(it.value)
            }
        }
    }

/**
 * 判断是否在主线程
 */
val isMainThread: Boolean
    get() = Looper.getMainLooper().thread == Thread.currentThread()