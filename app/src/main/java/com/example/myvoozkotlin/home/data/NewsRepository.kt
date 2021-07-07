package com.example.myvoozkotlin.home.data

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.data.repository.ApiRepository
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.home.domain.INewsRepository
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiRepository: ApiRepository)
    : INewsRepository {
    override fun loadNews(idGroup: Int): Event<MutableList<News>> {
        return Event(Status.LOADING, null, null);
    }
}