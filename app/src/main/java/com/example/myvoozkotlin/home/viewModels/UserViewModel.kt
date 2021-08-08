package com.example.myvoozkotlin.home.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val dbUtils: DbUtils
) : ViewModel() {

    val currentAuthUserResponse = MutableLiveData<Event<AuthUserModel>>()
    fun getCurrentAuthUser() = dbUtils.getCurrentAuthUser()
}