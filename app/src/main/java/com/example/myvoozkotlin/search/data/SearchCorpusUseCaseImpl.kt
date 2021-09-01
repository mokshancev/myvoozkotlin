package com.example.myvoozkotlin.search.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.search.domain.SearchCorpusUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCorpusUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchCorpusUseCase {
    override fun invoke(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>> = searchRepository.loadCorpusList(text, idUniversity)
}