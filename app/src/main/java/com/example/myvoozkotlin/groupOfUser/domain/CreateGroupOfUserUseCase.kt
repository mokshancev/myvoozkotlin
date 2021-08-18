package com.example.myvoozkotlin.groupOfUser.domain

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface CreateGroupOfUserUseCase {
    operator fun invoke(accessToken: String, idUser: Int, name: String, idGroup: Int): Flow<Event<InviteData>>
}