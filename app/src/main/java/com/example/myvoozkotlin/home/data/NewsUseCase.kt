package com.example.myvoozkotlin.home.data

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.data.repository.ApiRepository
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.INewsRepository
import com.example.myvoozkotlin.home.domain.INewsUseCase
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: INewsRepository
) : INewsUseCase {
    override fun invoke(idGroup: Int): Event<MutableList<News>> = newsRepository.loadNews(idGroup)
}