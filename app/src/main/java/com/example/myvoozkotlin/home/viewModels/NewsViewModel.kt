package com.example.myvoozkotlin.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    val newsResponse = MutableLiveData<Event<List<News>>>()
    fun loadNews(idGroup: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            newsUseCase(idGroup).collect {
                newsResponse.postValue(it)
            }
        }
    }

}