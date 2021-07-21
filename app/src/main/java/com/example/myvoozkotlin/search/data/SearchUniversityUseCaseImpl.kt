package com.example.myvoozkotlin.search.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUniversityUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchUniversityUseCase {
    override fun invoke(text: String): Flow<Event<List<SearchItem>>> = searchRepository.loadUniversityList(text)
}