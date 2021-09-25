package com.example.myvoozkotlin.auth.domain

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface AuthVkUseCase {
    operator fun invoke(accessToken: String, idUser : Int, idUniversity : Int, idGroup : Int, keyNotification : String): Flow<Event<AuthUser>>
}