package com.example.myvoozkotlin.data.repository

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.data.api.NewsApi
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val newsApi: NewsApi
){
    suspend fun loadNews(idGroup: Int): Response<List<News>> {
        return newsApi.loadNews(idGroup)
    }
}