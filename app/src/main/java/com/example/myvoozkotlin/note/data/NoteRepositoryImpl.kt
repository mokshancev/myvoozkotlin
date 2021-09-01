package com.example.myvoozkotlin.note.data

import com.example.myvoozkotlin.data.api.NoteApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteApi: NoteApi
) : NoteRepository {
    override fun loadUserNote(
        accessToken: String,
        idUser: Int,
        type: Int
    ): Flow<Event<List<Note>>> =
        flow<Event<List<Note>>> {
            emit(Event.loading())
            val apiResponse = noteApi.loadNoteList(accessToken, idUser, type)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun completedNote(
        accessToken: String,
        idUser: Int,
        notes: List<Int>
    ): Flow<Event<Any>> =
        flow<Event<Any>> {
            emit(Event.loading())
            val apiResponse = noteApi.completedNote(accessToken, idUser, notes)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun addNote(
        accessToken: String,
        idUser: Int,
        idObject: Int,
        title: String,
        text: String,
        date: String,
        markMe: Int,
        images: List<Int>
    ): Flow<Event<Note>> =
        flow<Event<Note>> {
            emit(Event.loading())
            val apiResponse = noteApi.addNote(accessToken, idUser, idObject, title, text, date, markMe, images)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }
}