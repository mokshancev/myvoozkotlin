package com.example.myvoozkotlin.search.di

import com.example.myvoozkotlin.search.api.SearchApi
import com.example.myvoozkotlin.search.data.*
import com.example.myvoozkotlin.search.domain.*
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
    fun provideSearchObjectUseCase(searchRepository: SearchRepository): SearchObjectUseCase {
        return SearchObjectUseCaseImpl(searchRepository)
    }

    @Provides
    fun provideSearchCorpusUseCase(searchRepository: SearchRepository): SearchCorpusUseCase {
        return SearchCorpusUseCaseImpl(searchRepository)
    }

    @Provides
    fun provideSearchRepository(searchApi: SearchApi) : SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }
}