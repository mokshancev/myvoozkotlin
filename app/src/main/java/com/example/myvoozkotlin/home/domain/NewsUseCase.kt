package com.example.myvoozkotlin.home.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    operator fun invoke(id_group: Int): Flow<Event<List<News>>>
}