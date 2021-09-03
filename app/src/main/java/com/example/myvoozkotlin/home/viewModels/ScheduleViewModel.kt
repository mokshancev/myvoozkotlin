package com.example.myvoozkotlin.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsUseCase
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDayUseCase: ScheduleDayUseCase
) : ViewModel() {

    val scheduleDayResponse = MutableLiveData<Event<List<List<Lesson>>>>()
    fun loadScheduleDay(idGroup: Int, week: Int, day: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            scheduleDayUseCase(idGroup, week, day).collect {
                scheduleDayResponse.postValue(it)
            }
        }
    }

}