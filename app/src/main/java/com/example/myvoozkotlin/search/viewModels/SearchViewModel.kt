package com.example.myvoozkotlin.search.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.search.domain.SearchCorpusUseCase
import com.example.myvoozkotlin.search.domain.SearchGroupUseCase
import com.example.myvoozkotlin.search.domain.SearchObjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUniversityUseCase: SearchUniversityUseCase,
    private val searchObjectUseCase: SearchObjectUseCase,
    private val searchCorpusUseCase: SearchCorpusUseCase,
    private val searchGroupUseCase: SearchGroupUseCase
) : ViewModel() {

    val searchResponse = MutableLiveData<Event<List<SearchItem>>>()
    fun loadUniversityList(text: String) {
        viewModelScope.launch {
            searchUniversityUseCase(text).collect {
                searchResponse.postValue(it)
            }
        }
    }

    fun loadGroupList(text: String, idUniversity: Int) {
        viewModelScope.launch {
            searchGroupUseCase(text, idUniversity).collect {
                searchResponse.postValue(it)
            }
        }
    }

    fun loadObjectList(text: String, idUniversity: Int) {
        viewModelScope.launch {
            searchObjectUseCase(text, idUniversity).collect {
                searchResponse.postValue(it)
            }
        }
    }

    fun loadCorpusList(text: String, idUniversity: Int) {
        viewModelScope.launch {
            searchCorpusUseCase(text, idUniversity).collect {
                searchResponse.postValue(it)
            }
        }
    }
}