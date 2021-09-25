package com.example.myvoozkotlin.note.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.domain.AddNoteUseCase
import com.example.myvoozkotlin.note.domain.CompletedNoteUseCase
import com.example.myvoozkotlin.note.domain.NoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteListUseCase: NoteListUseCase,
    private val completedNoteUseCase: CompletedNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    val noteListResponse = MutableLiveData<Event<List<Note>>>()
    fun loadUserNote(accessToken: String, idUser: Int, type: Int) {
        viewModelScope.launch {
            noteListUseCase(accessToken, idUser, type).collect {
                noteListResponse.postValue(it)
            }
        }
    }

    val addNoteResponse = MutableLiveData<Event<Note>>()
    fun addNote(accessToken: String, idUser: Int, idObject: Int, title: String, text: String, date: String, markMe: Int, images: List<Int>) {
        viewModelScope.launch {
            addNoteUseCase(accessToken, idUser, idObject, title, text, date, markMe, images).collect {
                addNoteResponse.postValue(it)
            }
        }
    }

    val completedNoteResponse = MutableLiveData<Event<Any>>()
    fun completedNote(accessToken: String, idUser: Int, notes: List<Int>) {
        viewModelScope.launch {
            completedNoteUseCase(accessToken, idUser, notes).collect {
                completedNoteResponse.postValue(it)
            }
        }
    }
}