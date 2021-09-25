package com.example.myvoozkotlin.user.api

import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
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

    @GET("profile?type=change_user_id_group")
    suspend fun changeIdGroup(@Query("access_token") accessToken: String,
                       @Query("user_id") id_user: Int,
                       @Query("id_group") idGroup: Int): Response<Boolean>

    @GET("profile?type=classroom")
    suspend fun getEmptyAuditory(@Query("date") date: String,
                                 @Query("id_corpus") idCorpus: Int,
                                 @Query("low_number") lowNumber: Int,
                                 @Query("upper_number") upperNumber: Int,
                                 @Query("id_university") idUniversity: Int): Response<List<List<Classroom>>>

    @Multipart
    @POST("profile?type=image_profile")
    suspend fun uploadPhoto(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Part("file\"; filename=\"pp.png\"") image: MultipartBody.Part
    ): Response<Boolean>
}