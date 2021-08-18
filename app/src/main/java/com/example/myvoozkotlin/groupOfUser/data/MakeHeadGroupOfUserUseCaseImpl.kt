package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.domain.*
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeHeadGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : MakeHeadGroupOfUserUseCase {
    override fun invoke(accessToken: String, idUser: Int, idSelUser: Int): Flow<Event<Boolean>> = groupOfUserRepository.makeHead(accessToken, idUser, idSelUser)
}