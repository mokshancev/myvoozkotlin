package com.example.myvoozkotlin.domain

import com.example.homelibrary.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("profile?type=news")
    suspend fun loadNews(@Query("id_group") id_group : Int): MutableList<News>
}