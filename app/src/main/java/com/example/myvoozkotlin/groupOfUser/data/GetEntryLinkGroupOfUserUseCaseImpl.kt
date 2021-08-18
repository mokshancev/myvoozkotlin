package com.example.myvoozkotlin.groupOfUser.data

import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.groupOfUser.domain.CreateGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GetEntryLinkGroupOfUserUseCase
import com.example.myvoozkotlin.groupOfUser.domain.GroupOfUserRepository
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEntryLinkGroupOfUserUseCaseImpl @Inject constructor(
    private val groupOfUserRepository: GroupOfUserRepository
) : GetEntryLinkGroupOfUserUseCase {
    override fun invoke(accessToken: String, idUser: Int): Flow<Event<EntryLink>> = groupOfUserRepository.getEntryLink(accessToken, idUser)
}