package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.data.api.SearchApi
import com.example.myvoozkotlin.search.data.SearchGroupUseCaseImpl
import com.example.myvoozkotlin.search.data.SearchRepositoryImpl
import com.example.myvoozkotlin.search.data.SearchUniversityUseCaseImpl
import com.example.myvoozkotlin.search.domain.SearchGroupUseCase
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    fun provideSearchUniversityUseCase(searchRepository: SearchRepository): SearchUniversityUseCase {
        return SearchUniversityUseCaseImpl(searchRepository)
    }

    @Provides
    fun provideSearchGroupUseCase(searchRepository: SearchRepository): SearchGroupUseCase {
        return SearchGroupUseCaseImpl(searchRepository)
    }

    @Provides
    fun provideSearchRepository(searchApi: SearchApi) : SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }
}