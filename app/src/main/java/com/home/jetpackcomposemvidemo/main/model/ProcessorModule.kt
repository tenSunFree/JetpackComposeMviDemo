package com.home.jetpackcomposemvidemo.main.model

import com.home.jetpackcomposemvidemo.common.ISchedulerProvider
import com.home.jetpackcomposemvidemo.main.intent.MainProcessor
import com.home.jetpackcomposemvidemo.main.model.IMainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ProcessorModule {

    @Provides
    fun provideMovieProcessor(repository: IMainRepository, schedulerProvider: ISchedulerProvider) =
            MainProcessor(repository, schedulerProvider)
}