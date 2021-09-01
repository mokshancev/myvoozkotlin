package com.example.myvoozkotlin.data.api

import com.example.myvoozkotlin.note.model.Note
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApi {
    @GET("profile?type=user_note")
    suspend fun loadNoteList(
        @Query("access_token") accessToken : String,
        @Query("user_id") idUser : Int,
        @Query("type_n") type : Int
    ): Response<List<Note>>

    @GET("profile?type=completed_user_note")
    suspend fun completedNote(
        @Query("access_token") accessToken : String,
        @Query("user_id") idUser : Int,
        @Query("notes[]") notes : List<Int>
    ): Response<Any>

    @GET("profile?type=add_user_note")
    suspend fun addNote(
        @Query("access_token") access_token: String?,
        @Query("user_id") user_id: Int,
        @Query("id_object") id_object: Int,
        @Query("title") title: String,
        @Query("text") text: String,
        @Query("date") date: String,
        @Query("mark_me") mark_me: Int?,
        @Query("images[]") images: List<Int>
    ): Response<Note>
}