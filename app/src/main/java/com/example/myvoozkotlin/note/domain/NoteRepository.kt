package com.example.myvoozkotlin.note.domain

import android.content.Context
import android.graphics.Bitmap
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun loadUserNote(accessToken: String, idUser: Int, type: Int): Flow<Event<List<Note>>>
    fun addNote(accessToken: String, idUser: Int, idObject: Int, title: String, text: String, date: String, markMe: Int, images: List<Int>): Flow<Event<Note>>
}