package com.example.myvoozkotlin.groupOfUser.domain

import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface InviteGroupOfUserUseCase {
    operator fun invoke(accessToken: String, idUser: Int, text: String): Flow<Event<InviteData>>
}