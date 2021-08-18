package com.example.myvoozkotlin.data.api

import com.example.homelibrary.model.AuthUser
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("profile?type=edit_account")
    suspend fun changeFullName(@Query("access_token") accessToken: String,
                       @Query("user_id") id_user: Int,
                       @Query("first_name") firstName: String,
                       @Query("last_name") secondName: String): Response<Boolean>

    @Multipart
    @POST("profile?type=image_profile")
    suspend fun uploadPhoto(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Part("file\"; filename=\"pp.png\"") image: MultipartBody.Part
    ): Response<Boolean>
}