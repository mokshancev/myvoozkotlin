package com.example.myvoozkotlin.home.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun loadNews(idGroup: Int): Flow<Event<List<News>>>
}