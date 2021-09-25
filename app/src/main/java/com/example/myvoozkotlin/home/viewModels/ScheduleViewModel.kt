package com.example.myvoozkotlin.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.AllScheduleUseCase
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDayUseCase: ScheduleDayUseCase,
    private val allScheduleUseCase: AllScheduleUseCase,
    private val scheduleDayRepository: ScheduleDayRepository
) : ViewModel() {

    val scheduleDayResponse = MutableLiveData<Event<List<List<Lesson>>>>()
    fun loadScheduleDay(idGroup: Int, week: Int, day: Int) {
        viewModelScope.launch {
            scheduleDayUseCase(idGroup, week, day).collect {
                scheduleDayResponse.postValue(it)
            }
        }
    }

    val getAllScheduleResponse = MutableLiveData<Event<List<Lesson>>>()
    fun getNextSchedule(number: Int, dayOfWeek: Int, week: Int) {
        viewModelScope.launch {
            scheduleDayRepository.getNextSchedule(number, dayOfWeek, week)
        }
    }

    val loadAllScheduleResponse = MutableLiveData<Event<List<Lesson>>>()
    fun loadAllSchedule(accessToken: String, idUser: Int) {
        viewModelScope.launch {
            allScheduleUseCase(accessToken, idUser).collect {
                loadAllScheduleResponse.postValue(it)
            }
        }
    }

//    fun removeAllSchedule(){
//        scheduleDayRepository.removeAllSchedule()
//    }
}