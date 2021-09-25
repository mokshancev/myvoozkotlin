package com.example.myvoozkotlin.home.api

import com.example.myvoozkotlin.models.news.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("profile?type=news")
    suspend fun loadNews(@Query("id_group") id_group : Int): Response<List<News>>
}