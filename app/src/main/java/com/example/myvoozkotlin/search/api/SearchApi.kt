package com.example.myvoozkotlin.search.api

import com.example.myvoozkotlin.models.SearchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("profile?type=institutions")
    suspend fun loadUniversityList(@Query("text") text : String): Response<List<SearchItem>>

    @GET("profile?type=objects")
    suspend fun loadObjectsList(@Query("text") text : String, @Query("id_university") idUniversity : Int): Response<List<SearchItem>>

    @GET("profile?type=groups")
    suspend fun loadGroupList(@Query("text") text : String, @Query("id_university") idUniversity : Int): Response<List<SearchItem>>

    @GET("profile?type=corpus")
    suspend fun loadCorpusList(@Query("text") text : String, @Query("id_university") idUniversity : Int): Response<List<SearchItem>>
}