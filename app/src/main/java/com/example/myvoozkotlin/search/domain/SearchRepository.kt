package com.example.myvoozkotlin.search.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun loadUniversityList(text: String): Flow<Event<List<SearchItem>>>
    fun loadGroupList(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>>
    fun loadObjectList(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>>
    fun loadCorpusList(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>>
}