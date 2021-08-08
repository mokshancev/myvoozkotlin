package com.example.myvoozkotlin.note.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.note.domain.AddNoteUseCase
import com.example.myvoozkotlin.note.domain.NoteListUseCase
import com.example.myvoozkotlin.search.domain.SearchGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteListUseCase: NoteListUseCase,
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
}