package com.example.myvoozkotlin.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homelibrary.model.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.data.NewsUseCase
import com.example.myvoozkotlin.home.domain.INewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
    ): ViewModel() {

    val newsResponse = MutableLiveData<Event<MutableList<News>>>()
    fun loadNews(idGroup: Int){
        newsResponse.postValue(newsUseCase.invoke(idGroup))
    }

}