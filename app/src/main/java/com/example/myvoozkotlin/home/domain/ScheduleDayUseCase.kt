package com.example.myvoozkotlin.home.domain

import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface ScheduleDayUseCase {
    operator fun invoke(idGroup: Int, week : Int, day : Int): Flow<Event<List<List<Lesson>>>>
}