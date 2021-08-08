package com.example.myvoozkotlin.note.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.note.domain.AddNoteUseCase
import com.example.myvoozkotlin.note.domain.NoteListUseCase
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