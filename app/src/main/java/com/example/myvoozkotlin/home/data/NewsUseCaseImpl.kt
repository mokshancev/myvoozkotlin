package com.example.myvoozkotlin.home.data

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : NewsUseCase {
    override fun invoke(idGroup: Int): Flow<Event<List<News>>> = newsRepository.loadNews(idGroup)
}