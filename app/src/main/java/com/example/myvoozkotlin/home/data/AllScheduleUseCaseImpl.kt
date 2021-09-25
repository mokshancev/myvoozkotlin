package com.example.myvoozkotlin.home.data

import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllScheduleUseCaseImpl @Inject constructor(
    private val scheduleDayRepository: ScheduleDayRepository
) : AllScheduleUseCase {
    override fun invoke(accessToken: String, idUser: Int): Flow<Event<List<Lesson>>> = scheduleDayRepository.loadAllSchedule(accessToken, idUser)
}