package com.example.myvoozkotlin.data.api

import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("profile?type=day_schedule")
    suspend fun loadDaySchedule(@Query("id_group") id_group : Int, @Query("week") week : Int, @Query("day") day : Int): Response<List<List<Lesson>>>
}