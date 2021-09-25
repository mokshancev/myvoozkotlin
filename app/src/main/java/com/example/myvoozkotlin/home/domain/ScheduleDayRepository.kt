package com.example.myvoozkotlin.home.domain

import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.model.LessonDbModel
import kotlinx.coroutines.flow.Flow

interface ScheduleDayRepository {
    fun loadScheduleDay(idGroup: Int, week : Int, day : Int): Flow<Event<List<List<Lesson>>>>
    fun loadAllSchedule(accessToken: String, idUser: Int): Flow<Event<List<Lesson>>>
    fun getNextSchedule(number: Int, dayOfWeek: Int, week: Int): Event<List<Lesson>>?
    fun getAllSchedule(): List<LessonDbModel>?
    fun removeAllSchedule()
}