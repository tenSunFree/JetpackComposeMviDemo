package com.home.jetpackcomposemvidemo.main.model

import io.reactivex.rxjava3.core.Single

class RemoteMainDataStore(private val api: MainApi) : MainDataStore {

    override fun getMovies(): Single<List<MainDataBean>> {
        return api.getData()
            .map { movieResponse ->
                movieResponse
                    .map { movie ->
                        MainDataBean(
                                albumId = movie.albumId,
                                id = movie.id,
                                title = movie.title,
                                url = movie.url,
                                thumbnailUrl = movie.thumbnailUrl
                        )
                    }
            }
    }
}