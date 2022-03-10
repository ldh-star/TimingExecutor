package com.liangguo.timingexecutor

import com.liangguo.timingexecutor.core.TaskManager
import com.liangguo.timingexecutor.core.TimingExecutorConfig
import com.liangguo.timingexecutor.core.isMainThread

/**
 * @author ldh
 * 时间: 2022/2/12 14:06
 * 邮箱: 2637614077@qq.com
 *
 * 有这么一个东西想实现：在开发相机应用时，当缩放手势执行中需要将缩放的数据显示到屏幕上，完了过后3秒后再消失
 * 手势执行中会不停的刷新，但是又不能太频繁的创建新线程和新对象，于是写了这个工具
 */
object TimingExecutor {

    /**
     * 延迟执行一个任务，后续传入相同id的任务会进行自动更新，延迟结束后这个任务就会执行。
     * @param delay 任务延迟多久执行。
     * @param id 任务的唯一标识，当有相同任务扔进来时会覆盖掉原来的。
     * @param execMainThread true：执行时切换到主线程。false：切换到子线程。默认为null：根据调用时的线程来自动决定。
     * @param exec 结束时执行的函数。
     */
    fun delayExecute(delay: Long, id: Any, execMainThread: Boolean? = null, exec: () -> Unit) {
        TaskManager.putTask(
            delay = delay,
            id = id,
            mainThread = execMainThread ?: isMainThread,
            exec = exec
        )
    }

    /**
     * 取消任务
     */
    fun cancelTask(id: Any) = TaskManager.cancelTask(id)

    /**
     * 进行配置
     */
    fun config(config: (TimingExecutorConfig) -> Unit) = this.run {
        config(TimingExecutorConfig)
    }

}