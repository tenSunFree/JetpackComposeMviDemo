package com.home.jetpackcomposemvidemo.main.model

import io.reactivex.rxjava3.core.Flowable

class MainRepository(
        private val remoteDataStore: MainDataStore
) : IMainRepository {

    override fun getData(): Flowable<List<MainDataBean>> {
        return remoteDataStore.getMovies().toFlowable()
    }
}