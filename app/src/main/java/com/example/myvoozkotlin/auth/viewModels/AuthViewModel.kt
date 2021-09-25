package com.example.myvoozkotlin.auth.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.auth.domain.AuthYaUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authVkUseCase: AuthVkUseCase,
    private val authYaUseCase: AuthYaUseCase
) : ViewModel() {

    val authVkResponse = MutableLiveData<Event<AuthUser>>()
    fun authVk(accessToken: String, idUser : Int, idUniversity : Int, idGroup : Int, keyNotification : String) {
        viewModelScope.launch {
            authVkUseCase(accessToken, idUser, idUniversity, idGroup, keyNotification).collect {
                authVkResponse.postValue(it)
            }
        }
    }

    val authYaResponse = MutableLiveData<Event<AuthUser>>()
    fun authYa(accessToken: String, idUniversity : Int, idGroup : Int, keyNotification : String) {

        viewModelScope.launch {
            authYaUseCase(accessToken, idUniversity, idGroup, keyNotification).collect {
                authYaResponse.postValue(it)
            }
        }
    }
}