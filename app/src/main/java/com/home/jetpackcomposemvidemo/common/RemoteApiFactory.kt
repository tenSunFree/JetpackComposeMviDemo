package com.home.jetpackcomposemvidemo.common

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.home.jetpackcomposemvidemo.BuildConfig
import com.home.jetpackcomposemvidemo.main.model.MainApi
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteApiFactory {

    fun makeMovieApi(): MainApi {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor())
        return makeMovieApi(okHttpClient, makeGson())
    }

    private fun makeMovieApi(okHttpClient: OkHttpClient, gson: Gson): MainApi {
        return makeRetrofit(okHttpClient, gson).create(MainApi::class.java)
    }

    private fun makeRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    private fun makeOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val httpLogger: HttpLoggingInterceptor.Logger by lazy {
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("more", "HTTP::TrendingService:: $message")
            }
        }
    }
}