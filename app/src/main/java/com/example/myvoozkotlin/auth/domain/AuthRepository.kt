package com.example.myvoozkotlin.auth.domain

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun authVk(accessToken: String, idUser : Int, idUniversity : Int, idGroup : Int, keyNotification : String): Flow<Event<AuthUser>>
    fun authYa(accessToken: String, idUniversity : Int, idGroup : Int, keyNotification : String): Flow<Event<AuthUser>>
}