package com.example.myvoozkotlin.groupOfUser.api

import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.homelibrary.model.UserShort
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupOfUserApi {

    @GET("profile?type=create_group_of_user")
    suspend fun createGroupOfUser(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("name_group") nameGroup: String,
        @Query("id_group") idGroup: Int
    ): Response<InviteData>

    @GET("profile?type=invite_group_of_user_code")
    suspend fun inviteGroupOfUser(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("text") text: String
    ): Response<InviteData>

    @GET("profile?type=logout_group_of_user")
    suspend fun logoutGroupOfUser(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int
    ): Response<Boolean>

    @GET("profile?type=change_group_of_user_id")
    suspend fun changeGroup(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("id_group") idGroup: Int
    ): Response<Boolean>

    @GET("profile?type=change_group_of_user_name")
    suspend fun changeName(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("text") text: String
    ): Response<Boolean>

    @GET("profile?type=get_users_group")
    suspend fun getUserList(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int
    ): Response<List<UserShort>>

    @GET("profile?type=get_entry_link")
    suspend fun getEntryLink(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int
    ): Response<EntryLink>

    @GET("profile?type=update_entry_link")
    suspend fun updateEntryLink(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int
    ): Response<EntryLink>

    @GET("profile?type=lock_invite_group_of_user")
    suspend fun lockEntryLink(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("state") state: Int
    ): Response<Boolean>

    @GET("profile?type=to_make_the_head_user")
    suspend fun makeUserHead(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("sel_user_id") idSelUser: Int
    ): Response<Boolean>

    @GET("profile?type=delete_user_from_group")
    suspend fun removeUser(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("sel_user_id") idSelUser: Int
    ): Response<Boolean>

    @GET("profile?type=get_group_of_user")
    suspend fun getGroupOfUser(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int
    ): Response<InviteData>
}