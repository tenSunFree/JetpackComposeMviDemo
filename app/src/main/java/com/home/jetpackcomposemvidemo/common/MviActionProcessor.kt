package com.home.jetpackcomposemvidemo.common

import io.reactivex.rxjava3.core.FlowableTransformer

interface MviActionProcessor<A : MviAction, R : MviResult> {

    val schedulerProvider: ISchedulerProvider

    fun transformFromAction(): FlowableTransformer<A, R>
}