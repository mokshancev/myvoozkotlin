package com.example.myvoozkotlin.home.di

import com.example.myvoozkotlin.home.api.NewsApi
import com.example.myvoozkotlin.home.data.NewsRepositoryImpl
import com.example.myvoozkotlin.home.data.NewsUseCaseImpl
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase{
        return NewsUseCaseImpl(newsRepository)
    }

    @Provides
    fun provideNewsRepository(newsApi: NewsApi) : NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }
}