package com.example.myvoozkotlin.user.domain

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun changeUserFullName(accessToken: String, idUser : Int, firstName : String, secondName : String): Flow<Event<Boolean>>
    fun changeAuthUserListener(onAuthUserChange: OnAuthUserChange?)
}