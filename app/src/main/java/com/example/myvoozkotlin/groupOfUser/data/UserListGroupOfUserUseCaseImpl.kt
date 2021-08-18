package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.InviteData
import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.groupOfUser.domain.CreateGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GroupOfUserRepository
import com.example.myvoozkotlin.groupOfUser.domain.UserListGroupOfUserUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : UserListGroupOfUserUseCase {
    override fun invoke(accessToken: String, idUser: Int): Flow<Event<List<UserShort>>> = groupOfUserRepository.getUserList(accessToken, idUser)
}