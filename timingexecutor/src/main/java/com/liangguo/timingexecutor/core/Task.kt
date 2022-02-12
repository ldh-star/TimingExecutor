package com.liangguo.timingexecutor.core


/**
 * @author ldh
 * 时间: 2022/2/12 14:16
 * 邮箱: 2637614077@qq.com
 *
 * @param finishTime 该任务结束的时间
 * @param id 该任务的id，唯一标识
 * @param mainThread 是否在主线程中执行
 * @param exec 结束时要执行的任务
 */
internal data class Task(
    var finishTime: Long,
    val id: Any,
    var mainThread: Boolean,
    var exec: () -> Unit
)