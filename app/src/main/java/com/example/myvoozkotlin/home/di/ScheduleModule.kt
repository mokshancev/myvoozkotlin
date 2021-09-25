package com.example.myvoozkotlin.home.di

import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.home.api.ScheduleApi
import com.example.myvoozkotlin.home.data.AllScheduleUseCaseImpl
import com.example.myvoozkotlin.home.data.ScheduleDayRepositoryImpl
import com.example.myvoozkotlin.home.data.ScheduleDayUseCaseImpl
import com.example.myvoozkotlin.home.domain.AllScheduleUseCase
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
    fun provideAllScheduleUseCase(scheduleDayRepository: ScheduleDayRepository): AllScheduleUseCase{
        return AllScheduleUseCaseImpl(scheduleDayRepository)
    }

    @Provides
    fun provideScheduleDayRepository(scheduleApi: ScheduleApi, dbUtils: DbUtils) : ScheduleDayRepository {
        return ScheduleDayRepositoryImpl(scheduleApi, dbUtils)
    }
}