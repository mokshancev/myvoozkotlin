package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.domain.*
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeNameGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : ChangeNameGroupOfUserUseCase {

    override fun invoke(accessToken: String, idUser: Int, name: String): Flow<Event<Boolean>> = groupOfUserRepository.changeName(accessToken, idUser, name)
}