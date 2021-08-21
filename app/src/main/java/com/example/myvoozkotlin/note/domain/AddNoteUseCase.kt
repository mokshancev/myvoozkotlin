package com.example.myvoozkotlin.note.domain

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import kotlinx.coroutines.flow.Flow
interface AddNoteUseCase {
    operator fun invoke(accessToken: String, idUser: Int, idObject: Int, title: String, text: String, date: String, markMe: Int, images: List<Int>): Flow<Event<Note>>
}