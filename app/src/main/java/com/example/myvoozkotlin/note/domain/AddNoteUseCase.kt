package com.example.myvoozkotlin.note.domain

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow
interface AddNoteUseCase {
    operator fun invoke(accessToken: String, idUser: Int, idObject: Int, title: String, text: String, date: String, markMe: Int, images: List<Int>): Flow<Event<Note>>
}