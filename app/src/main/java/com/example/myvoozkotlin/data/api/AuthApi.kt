package com.example.myvoozkotlin.data.api

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.models.news.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    @GET("profile?type=auth_2")
    suspend fun authVk(@Query("access_token") accessToken: String,
                       @Query("user_id") id_user: Int,
                       @Query("id_university") idUniversity: Int,
                       @Query("id_group") idGroup: Int,
                       @Query("key_notif") keyNotification: String): Response<AuthUser>

    @GET("profile?type=auth_2_ya")
    suspend fun authYa(@Query("access_token") accessToken: String,
                       @Query("id_university") idUniversity: Int,
                       @Query("id_group") idGroup: Int,
                       @Query("key_notif") keyNotification: String): Response<AuthUser>
}