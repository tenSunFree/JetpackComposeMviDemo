package com.home.jetpackcomposemvidemo.main.model

import io.reactivex.rxjava3.core.Single

interface MainDataStore {

    fun getMovies(): Single<List<MainDataBean>>
}