package com.home.jetpackcomposemvidemo.main.model

import io.reactivex.rxjava3.core.Flowable

interface IMainRepository {

    fun getData(): Flowable<List<MainDataBean>>
}