package com.example.myvoozkotlin.home.domain

import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface ScheduleDayRepository {
    fun loadScheduleDay(idGroup: Int, week : Int, day : Int): Flow<Event<List<List<Lesson>>>>
}