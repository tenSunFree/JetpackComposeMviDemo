package com.home.jetpackcomposemvidemo.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppPresentationModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): ISchedulerProvider = SchedulerProvider()
}