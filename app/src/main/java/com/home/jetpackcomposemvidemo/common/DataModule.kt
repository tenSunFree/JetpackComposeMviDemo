package com.home.jetpackcomposemvidemo.common

import com.home.jetpackcomposemvidemo.main.model.IMainRepository
import com.home.jetpackcomposemvidemo.main.model.MainDataStore
import com.home.jetpackcomposemvidemo.main.model.MainRepository
import com.home.jetpackcomposemvidemo.main.model.RemoteMainDataStore
import com.home.jetpackcomposemvidemo.main.model.MainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object DataModule {

    @Provides
    @Named("remote")
    fun provideRemoteMainDataStore(api: MainApi): MainDataStore = RemoteMainDataStore(api)

    @Provides
    fun provideMovieRepository(
            @Named("remote") remoteMainDataStore: MainDataStore
    ): IMainRepository = MainRepository(remoteMainDataStore)
}

@Module
@InstallIn(ApplicationComponent::class)
object AppDataModule {

    @Provides
    @Singleton
    fun provideMainApi(): MainApi = RemoteApiFactory.makeMovieApi()
}