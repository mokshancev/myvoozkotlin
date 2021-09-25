package com.example.myvoozkotlin.notification.data.api

import com.example.homelibrary.model.Notification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApi {
    @GET("profile?type=user_notifications")
    suspend fun loadNotificationUser(@Query("access_token") accessToken : String, @Query("user_id") idUser : Int, @Query("type_n") type : Int): Response<List<Notification>>

    @GET("profile?type=user_notifications")
    suspend fun loadUserWithUniversityNotificationUser(@Query("access_token") accessToken : String, @Query("user_id") idUser : Int, @Query("type_n") type : Int, @Query("id_university") idUniversity : Int): Response<List<Notification>>

    @GET("profile?type=user_notifications")
    suspend fun loadUniversityNotification(@Query("id_university") type : Int): Response<List<Notification>>
}