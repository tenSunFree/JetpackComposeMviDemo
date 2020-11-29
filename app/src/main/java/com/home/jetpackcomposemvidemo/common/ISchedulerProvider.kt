package com.home.jetpackcomposemvidemo.common

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulerProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun ui(): Scheduler
}