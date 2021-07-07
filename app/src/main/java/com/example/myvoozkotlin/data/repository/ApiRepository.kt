package com.example.myvoozkotlin.data.repository

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.data.api.INewsApi
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    val newsApi: INewsApi
){
    suspend fun loadNews(idGroup: Int): Response<MutableList<News>>{
        return newsApi.loadNews(idGroup)
    }
}