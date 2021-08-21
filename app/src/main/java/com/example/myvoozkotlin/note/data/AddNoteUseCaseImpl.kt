package com.example.myvoozkotlin.note.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.domain.AddNoteUseCase
import com.example.myvoozkotlin.note.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddNoteUseCase {
    override fun invoke(
        accessToken: String,
        idUser: Int,
        idObject: Int,
        title: String,
        text: String,
        date: String,
        markMe: Int,
        images: List<Int>
    ): Flow<Event<Note>> = noteRepository.addNote(accessToken, idUser, idObject, title, text, date, markMe, images)
}