package com.example.myvoozkotlin.home.di

import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    fun provideNewsApi(retrofit: Retrofit) : NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideScheduleApi(retrofit: Retrofit) : ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }
}