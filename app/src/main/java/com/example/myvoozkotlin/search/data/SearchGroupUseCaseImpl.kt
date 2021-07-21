package com.example.myvoozkotlin.search.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.search.domain.SearchGroupUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGroupUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchGroupUseCase {
    override fun invoke(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>> = searchRepository.loadGroupList(text, idUniversity)
}