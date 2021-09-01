package com.example.myvoozkotlin.note.domain

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun loadUserNote(accessToken: String, idUser: Int, type: Int): Flow<Event<List<Note>>>
    fun completedNote(accessToken: String, idUser: Int, notes: List<Int>): Flow<Event<Any>>
    fun addNote(accessToken: String, idUser: Int, idObject: Int, title: String, text: String, date: String, markMe: Int, images: List<Int>): Flow<Event<Note>>
}