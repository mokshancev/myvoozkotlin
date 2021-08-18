package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.domain.ChangeIdGroupGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.CreateGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GroupOfUserRepository
import com.example.myvoozkotlin.groupOfUser.domain.LogoutGroupOfUserUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeIdGroupGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : ChangeIdGroupGroupOfUserUseCase {
    override fun invoke(
        accessToken: String,
        idUser: Int,
        idGroup: Int
    ): Flow<Event<Boolean>> = groupOfUserRepository.changeIdGroup(accessToken, idUser, idGroup)
}