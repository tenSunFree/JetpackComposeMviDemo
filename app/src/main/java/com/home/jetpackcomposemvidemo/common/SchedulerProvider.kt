package com.home.jetpackcomposemvidemo.common

import com.home.jetpackcomposemvidemo.common.ISchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProvider : ISchedulerProvider {

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}