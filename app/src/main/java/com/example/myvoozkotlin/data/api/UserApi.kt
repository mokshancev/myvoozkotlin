package com.example.myvoozkotlin.data.api

import com.example.homelibrary.model.AuthUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("profile?type=edit_account")
    suspend fun changeFullName(@Query("access_token") accessToken: String,
                       @Query("user_id") id_user: Int,
                       @Query("first_name") firstName: String,
                       @Query("last_name") secondName: String): Response<Boolean>
}