package com.example.myvoozkotlin.home.data

import android.util.Log
import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.home.api.ScheduleApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import com.example.myvoozkotlin.home.model.LessonDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleDayRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi,
    private val dbUtils: DbUtils
) : ScheduleDayRepository {
    override fun loadScheduleDay(
        idGroup: Int,
        week: Int,
        day: Int
    ): Flow<Event<List<List<Lesson>>>> =
        flow<Event<List<List<Lesson>>>> {
            emit(Event.loading())
            val apiResponse = scheduleApi.loadDaySchedule(idGroup, week, day)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else {
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun loadAllSchedule(
        accessToken: String,
        idUser: Int
    ): Flow<Event<List<Lesson>>> = flow<Event<List<Lesson>>> {
        emit(Event.loading())
        val apiResponse = scheduleApi.loadAllSchedule(accessToken, idUser)

        if (apiResponse.isSuccessful && apiResponse.body() != null) {
            apiResponse.body()!!.forEach {
                Log.d("breberberb", "v erergerggerg")
                dbUtils.addSchedule(lessonToDbModel(it))
            }
            emit(Event.success(apiResponse.body()!!))
        } else {
            emit(Event.error("lol"))
        }
    }.catch { e ->
        emit(Event.error("lol2"))
        e.printStackTrace()
    }

    override fun getNextSchedule(number: Int, dayOfWeek: Int, week: Int): Event<List<Lesson>>? {
        val scheduleList = getAllSchedule()
        if (scheduleList == null) {
            return null
        } else {
            var id: Int? = null
            var minNumber: Int? = null
            var minDayOfWeek: Int? = null
            var minWeek: Int? = null
            scheduleList.forEach {
                if (
                    it.minWeek <= week &&
                    it.maxWeek >= week
                ){

                }
            }
        }
        return null
    }

    override fun getAllSchedule(): List<LessonDbModel>? {
        return dbUtils.getAllSchedule()
    }

    override fun removeAllSchedule() {
        dbUtils.removeAllSchedule()
    }

    private fun lessonToDbModel(lesson: Lesson): LessonDbModel {
        lesson.apply {
            return LessonDbModel(
                id,
                name,
                typeName,
                classroom,
                teacher,
                firstTime,
                lastTime,
                number,
                minWeek,
                maxWeek,
                dayOfWeek
            )
        }
    }
}