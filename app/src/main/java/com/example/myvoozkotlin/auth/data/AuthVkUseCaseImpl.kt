package com.example.myvoozkotlin.auth.data

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthVkUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthVkUseCase {
    override fun invoke(accessToken: String, idUser : Int, idUniversity : Int, idGroup : Int, keyNotification : String): Flow<Event<AuthUser>>
    = authRepository.authVk(accessToken, idUser, idUniversity, idGroup, keyNotification)
}