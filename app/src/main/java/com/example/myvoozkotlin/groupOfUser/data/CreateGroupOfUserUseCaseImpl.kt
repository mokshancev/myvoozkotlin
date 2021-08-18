package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.domain.CreateGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GroupOfUserRepository
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : CreateGroupOfUserUseCase {
    override fun invoke(accessToken: String, idUser: Int, name: String, idGroup: Int): Flow<Event<InviteData>> = groupOfUserRepository.createGroupOfUser(accessToken, idUser, name, idGroup)
}