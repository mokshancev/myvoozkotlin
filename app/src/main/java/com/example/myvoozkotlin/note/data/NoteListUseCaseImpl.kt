package com.example.myvoozkotlin.note.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.domain.NoteListUseCase
import com.example.myvoozkotlin.note.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteListUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : NoteListUseCase {
    override fun invoke(accessToken: String, idUser: Int, type: Int): Flow<Event<List<Note>>> = noteRepository.loadUserNote(accessToken, idUser, type)
}