package com.example.myvoozkotlin.data.api

import com.example.homelibrary.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface INewsApi {
    @GET("profile?type=news")
    suspend fun loadNews(@Query("id_group") idGroup: Int): Response<MutableList<News>>
}