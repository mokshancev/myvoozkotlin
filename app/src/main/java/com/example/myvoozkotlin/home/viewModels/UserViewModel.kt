package com.example.myvoozkotlin.home.viewModels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val changeFullNameUseCase: ChangeFullNameUseCase,
    private val userRepository: UserRepository,
    private val dbUtils: DbUtils
) : ViewModel() {

    val currentAuthUserResponse = MutableLiveData<Event<AuthUserModel>>()
    fun getCurrentAuthUser() = dbUtils.getCurrentAuthUser()

    fun updateGroupOfUser(groupOfUserModel: GroupOfUserModel){
        val authUserModel = dbUtils.getCurrentAuthUser()
        authUserModel.groupOfUser = groupOfUserModel
        dbUtils.setCurrentAuthUser(authUserModel)
    }

    val changeFullNameResponse = MutableLiveData<Event<Boolean>>()
    fun changeFullName(accessToken: String, idUser : Int, firstName : String, secondName : String) {
        viewModelScope.launch {
            changeFullNameUseCase(accessToken, idUser, firstName, secondName).collect {
                changeFullNameResponse.postValue(it)
            }
        }
    }

    val uploadImageResponse = MutableLiveData<Event<Boolean>>()
    fun uploadImage(bitmap: Bitmap, accessToken: String, idUser: Int, type: String) {
        viewModelScope.launch {
            Log.d("bwrbwbwrb", "bwerbwbweb1")
            uploadImageResponse.postValue(Event.loading())
            Log.d("bwrbwbwrb", "bwerbwbweb2")
            Utils.uploadImage(bitmap, accessToken, idUser, type)
            Log.d("bwrbwbwrb", "bwerbwbwe3")
            uploadImageResponse.postValue(Event.success(true))
        }
    }

    fun changeAuthUserListener(onAuthUserChange: OnAuthUserChange?){
        userRepository.changeAuthUserListener(onAuthUserChange)
    }
}