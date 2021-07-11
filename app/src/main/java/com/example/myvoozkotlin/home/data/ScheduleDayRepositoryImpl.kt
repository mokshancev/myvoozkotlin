package com.example.myvoozkotlin.home.data

import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleDayRepositoryImpl @Inject constructor(
    private val newsApi: ScheduleApi
) : ScheduleDayRepository {
    override fun loadScheduleDay(idGroup: Int, week: Int, day: Int): Flow<Event<List<List<Lesson>>>> =
        flow<Event<List<List<Lesson>>>> {
            emit(Event.loading())
            val apiResponse = newsApi.loadDaySchedule(idGroup, week, day)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                //emit(Event.error("lol"))
            }
        }.catch { e ->
            //emit(Event.error("lol2"))
            e.printStackTrace()
        }
}