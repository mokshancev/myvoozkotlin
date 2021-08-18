package com.example.myvoozkotlin.user.domain

import com.example.homelibrary.model.AuthUser
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface ChangeFullNameUseCase {
    operator fun invoke(accessToken: String, idUser : Int, firstName : String, secondName : String): Flow<Event<Boolean>>
}