package com.example.myvoozkotlin.groupOfUser.domain

import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface MakeHeadGroupOfUserUseCase {
    operator fun invoke(accessToken: String, idUser: Int, idSelUser: Int): Flow<Event<Boolean>>
}