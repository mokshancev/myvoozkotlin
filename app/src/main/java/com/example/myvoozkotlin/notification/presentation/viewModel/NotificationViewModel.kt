package com.example.myvoozkotlin.notification.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.notification.domain.NotificationListUseCase
import com.example.myvoozkotlin.notification.domain.UniversityNotificationListUseCase
import com.example.myvoozkotlin.notification.domain.UserWithUniversityNotificationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationListUseCase: NotificationListUseCase,
    private val universityNotificationListUseCase: UniversityNotificationListUseCase,
    private val userWithUniversityNotificationListUseCase: UserWithUniversityNotificationListUseCase
) : ViewModel() {

    val notificationListResponse = MutableLiveData<Event<List<Notification>>>()
    fun loadUserNotification(accessToken: String, idUser: Int, type: Int) {
        viewModelScope.launch {
            notificationListUseCase(accessToken, idUser, type).collect {
                notificationListResponse.postValue(it)
            }
        }
    }
    fun loadUserWithUniversityNotification(accessToken: String, idUser: Int, type: Int, idUniversity: Int) {
        viewModelScope.launch {
            userWithUniversityNotificationListUseCase(accessToken, idUser, type, idUniversity).collect {
                notificationListResponse.postValue(it)
            }
        }
    }

    fun loadUniversityNotification(idUniversity: Int) {
        viewModelScope.launch {
            universityNotificationListUseCase(idUniversity).collect {
                notificationListResponse.postValue(it)
            }
        }
    }
}