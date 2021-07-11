package com.example.myvoozkotlin.home.data

import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleDayUseCaseImpl @Inject constructor(
    private val scheduleDayRepository: ScheduleDayRepository
) : ScheduleDayUseCase {
    override fun invoke(idGroup: Int, week : Int, day : Int): Flow<Event<List<List<Lesson>>>> = scheduleDayRepository.loadScheduleDay(idGroup, week, day)
}