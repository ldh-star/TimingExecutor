package com.liangguo.timingexecutor.core

import java.lang.ref.WeakReference


/**
 * @author ldh
 * 时间: 2022/3/10 10:59
 * 邮箱: 2637614077@qq.com
 *
 * @param id 该任务的id的弱引用
 * @param finishTime 该任务结束的时间
 * @param mainThread 是否在主线程中执行
 * @param exec 结束时要执行的任务的弱引用
 */
internal data class WeakRefTask(
    val id: WeakReference<out Any>,
    var finishTime: Long,
    var mainThread: Boolean,
    var exec: WeakReference<() -> Unit>
) {

    fun overrideParams(
        delay: Long,
        mainThread: Boolean,
        exec: () -> Unit
    ) {
        this.finishTime = System.currentTimeMillis() + delay
        this.mainThread = mainThread
        if (this.exec.get() != exec) {
            this.exec = WeakReference(exec)
        }
    }

    override fun toString(): String {
        return """
            ${javaClass.name}  id:$id  finishTime:$finishTime  mainThread:$mainThread
        """.trimIndent()
    }

}