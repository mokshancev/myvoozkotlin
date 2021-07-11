package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import com.example.myvoozkotlin.home.data.NewsRepositoryImpl
import com.example.myvoozkotlin.home.data.NewsUseCaseImpl
import com.example.myvoozkotlin.home.data.ScheduleDayRepositoryImpl
import com.example.myvoozkotlin.home.data.ScheduleDayUseCaseImpl
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ScheduleModule {
    @Provides
    fun provideScheduleDayUseCase(scheduleDayRepository: ScheduleDayRepository): ScheduleDayUseCase{
        return ScheduleDayUseCaseImpl(scheduleDayRepository)
    }

    @Provides
    fun provideScheduleDayRepository(scheduleApi: ScheduleApi) : ScheduleDayRepository {
        return ScheduleDayRepositoryImpl(scheduleApi)
    }
}