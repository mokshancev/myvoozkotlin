package com.example.myvoozkotlin.home.api

import com.example.myvoozkotlin.home.model.Lesson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("profile?type=day_schedule")
    suspend fun loadDaySchedule(@Query("id_group") id_group : Int, @Query("week") week : Int, @Query("day") day : Int): Response<List<List<Lesson>>>

    @GET("profile?type=get_group_schedule_list")
    suspend fun loadAllSchedule(@Query("access_token") accessToken : String, @Query("user_id") idUser : Int): Response<List<Lesson>>
}