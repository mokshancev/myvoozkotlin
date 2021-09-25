package com.example.myvoozkotlin.home.domain

import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface AllScheduleUseCase {
    operator fun invoke(accessToken: String, idUser: Int): Flow<Event<List<Lesson>>>
}