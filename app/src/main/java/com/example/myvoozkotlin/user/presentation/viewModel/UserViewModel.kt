package com.example.myvoozkotlin.user.presentation.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.ChangeIdGroupUserUseCase
import com.example.myvoozkotlin.user.domain.EmptyAuditoryUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val changeFullNameUseCase: ChangeFullNameUseCase,
    private val userRepository: UserRepository,
    private val emptyAuditoryUseCase: EmptyAuditoryUseCase,
    private val changeIdGroupUserUseCase: ChangeIdGroupUserUseCase,
    private val dbUtils: DbUtils,
    private val realm: Realm
) : ViewModel() {

    fun getCurrentAuthUser() = dbUtils.getCurrentAuthUser()

    private var userRealm: RealmResults<AuthUserModel> =
        realm.where(AuthUserModel::class.java).findAll()

    init {
        userRealm.addChangeListener(RealmChangeListener {
            authUserChange.postValue(Any())
        })
    }

    val authUserChange = MutableLiveData<Any>()

    fun setCurrentUser(authUserModel: AuthUserModel) {
        dbUtils.setCurrentAuthUser(authUserModel)
    }

    fun removeCurrentUser() {
        dbUtils.removeCurrentUser()
    }

    val changeFullNameResponse = MutableLiveData<Event<Boolean>>()
    fun changeFullName(accessToken: String, idUser: Int, firstName: String, secondName: String) {
        viewModelScope.launch {
            changeFullNameUseCase(accessToken, idUser, firstName, secondName).collect {
                changeFullNameResponse.postValue(it)
            }
        }
    }

    val changeIdGroupUserResponse = MutableLiveData<Event<Boolean>>()
    fun changeIdGroupUser(accessToken: String, idUser: Int, nameGroup: String, idGroup: Int) {
        viewModelScope.launch {
            changeIdGroupUserUseCase(accessToken, idUser, nameGroup, idGroup).collect {
                changeIdGroupUserResponse.postValue(it)
            }
        }
    }

    val emptyClassroomResponse = MutableLiveData<Event<List<List<Classroom>>>>()
    fun getEmptyClassroom(date: String, idCorpus: Int, lowNumber: Int, upperNumber: Int, idUniversity: Int) {
        viewModelScope.launch {
            emptyAuditoryUseCase(date, idCorpus, lowNumber, upperNumber, idUniversity).collect {
                emptyClassroomResponse.postValue(it)
            }
        }
    }

    val uploadImageResponse = MutableLiveData<Event<Boolean>>()
    fun uploadImage(bitmap: Bitmap, accessToken: String, idUser: Int, type: String) {
        viewModelScope.launch {
            uploadImageResponse.postValue(Event.loading())
            Utils.uploadImage(bitmap, accessToken, idUser, type)
            uploadImageResponse.postValue(Event.success(true))
        }
    }

    fun getIdUniversity(): Int {
        return userRepository.getIdUniversity()
    }

    fun getNameGroup(): String {
        return userRepository.getNameGroup()
    }

    fun getNameUniversity(): String {
        return userRepository.getNameUniversity()
    }

    fun getIdGroup(): Int {
        return userRepository.getIdGroup()
    }

    override fun onCleared() {
        super.onCleared()
        userRealm.removeAllChangeListeners()
    }
}