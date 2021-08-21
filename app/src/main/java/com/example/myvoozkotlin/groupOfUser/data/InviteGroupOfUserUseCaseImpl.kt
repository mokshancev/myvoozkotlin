package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.InviteGroupOfUserFragment
import com.example.myvoozkotlin.groupOfUser.domain.CreateGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GroupOfUserRepository
import com.example.myvoozkotlin.groupOfUser.domain.InviteGroupOfUserUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InviteGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : InviteGroupOfUserUseCase {
    override fun invoke(accessToken: String, idUser: Int, text: String): Flow<Event<InviteData>>  = groupOfUserRepository.inviteGroupOfUser(accessToken, idUser, text)
}