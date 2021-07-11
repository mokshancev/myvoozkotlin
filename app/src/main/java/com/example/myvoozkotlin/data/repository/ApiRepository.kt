package com.example.myvoozkotlin.data.repository

import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val scheduleApi: ScheduleApi
){
    suspend fun loadNews(idGroup: Int): Response<List<News>> {
        return newsApi.loadNews(idGroup)
    }

    suspend fun loadDaySchedule(idGroup: Int, week : Int, day : Int): Response<List<List<Lesson>>> {
        return scheduleApi.loadDaySchedule(idGroup, week, day)
    }
}