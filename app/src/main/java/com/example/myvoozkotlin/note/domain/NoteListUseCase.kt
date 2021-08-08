package com.example.myvoozkotlin.note.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow
interface NoteListUseCase {
    operator fun invoke(accessToken: String, idUser: Int, type: Int): Flow<Event<List<Note>>>
}