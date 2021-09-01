package com.example.myvoozkotlin.groupOfUser.domain

import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface GroupOfUserRepository {
    fun createGroupOfUser(accessToken: String, idUser: Int, name: String, idGroup: Int): Flow<Event<InviteData>>
    fun inviteGroupOfUser(accessToken: String, idUser: Int, text: String): Flow<Event<InviteData>>
    fun logoutGroupOfUser(accessToken: String, idUser: Int): Flow<Event<Boolean>>
    fun changeIdGroup(accessToken: String, idUser: Int, idGroup: Int): Flow<Event<Boolean>>
    fun changeName(accessToken: String, idUser: Int, text: String): Flow<Event<Boolean>>
    fun getUserList(accessToken: String, idUser: Int): Flow<Event<List<UserShort>>>
    fun getEntryLink(accessToken: String, idUser: Int): Flow<Event<EntryLink>>
    fun updateEntryLink(accessToken: String, idUser: Int): Flow<Event<EntryLink>>
    fun lockEntryLink(accessToken: String, idUser: Int, state: Int): Flow<Event<Boolean>>
    fun makeHead(accessToken: String, idUser: Int, idSelUser: Int): Flow<Event<Boolean>>
    fun removeUser(accessToken: String, idUser: Int, idSelUser: Int): Flow<Event<Boolean>>
    fun getGroupOfUser(accessToken: String, idUser: Int): Flow<Event<InviteData>>
}