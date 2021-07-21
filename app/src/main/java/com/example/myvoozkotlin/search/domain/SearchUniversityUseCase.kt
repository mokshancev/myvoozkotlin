package com.example.myvoozkotlin.search.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchUniversityUseCase {
    operator fun invoke(text: String): Flow<Event<List<SearchItem>>>
}