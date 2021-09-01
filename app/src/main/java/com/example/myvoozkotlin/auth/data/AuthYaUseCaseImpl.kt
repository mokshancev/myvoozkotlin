package com.example.myvoozkotlin.auth.data

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.auth.domain.AuthYaUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthYaUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthYaUseCase {
    override fun invoke(accessToken: String, idUniversity : Int, idGroup : Int, keyNotification : String): Flow<Event<AuthUser>>
    = authRepository.authYa(accessToken, idUniversity, idGroup, keyNotification)
}