package com.home.jetpackcomposemvidemo.main.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MainApi {

    @GET("photos")
    fun getData(
    ): Single<MainDataResponse>
}