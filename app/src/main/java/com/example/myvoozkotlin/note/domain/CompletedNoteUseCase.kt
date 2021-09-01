package com.example.myvoozkotlin.note.domain

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import kotlinx.coroutines.flow.Flow
interface CompletedNoteUseCase {
    operator fun invoke(accessToken: String, idUser: Int, notes: List<Int>): Flow<Event<Any>>
}