package com.example.myvoozkotlin.groupOfUser.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homelibrary.model.EntryLink
import com.example.homelibrary.model.InviteData
import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.groupOfUser.domain.*
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import com.example.myvoozkotlin.models.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupOfUserViewModel @Inject constructor(
    private val createGroupOfUserUseCase: CreateGroupOfUserUseCase,
    private val changeIdGroupGroupOfUserUseCase: ChangeIdGroupGroupOfUserUseCase,
    private val changeNameGroupOfUserUseCase: ChangeNameGroupOfUserUseCase,
    private val userListGroupOfUserUseCase: UserListGroupOfUserUseCase,
    private val getEntryLinkGroupOfUserUseCase: GetEntryLinkGroupOfUserUseCase,
    private val makeHeadGroupOfUserUseCase: MakeHeadGroupOfUserUseCase,
    private val removeUserGroupOfUserUseCase: RemoveUserGroupOfUserUseCase,
    private val updateEntryLinkGroupOfUserUseCase: UpdateEntryLinkGroupOfUserUseCase,
    private val lockEntryLinkGroupOfUserUseCase: LockEntryLinkGroupOfUserUseCase,
    private val inviteGroupOfUserUseCase: InviteGroupOfUserUseCase,
    private val groupOfUserUseCase: GetGroupOfUserUseCase,
    private val dbUtils: DbUtils,
    private val logoutGroupOfUserUseCase: LogoutGroupOfUserUseCase
) : ViewModel() {

    val createGroupOfUserResponse = MutableLiveData<Event<InviteData>>()
    fun createGroupOfUser(accessToken: String, idUser: Int, name: String, idGroup: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            createGroupOfUserUseCase(accessToken, idUser, name, idGroup).collect {
                createGroupOfUserResponse.postValue(it)
            }
        }
    }

    val inviteGroupOfUserResponse = MutableLiveData<Event<InviteData>>()
    fun inviteGroupOfUser(accessToken: String, idUser: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            inviteGroupOfUserUseCase(accessToken, idUser, name).collect {
                inviteGroupOfUserResponse.postValue(it)
            }
        }
    }

    val logoutGroupOfUserResponse = MutableLiveData<Event<Boolean>>()
    fun logoutGroupOfUser(accessToken: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            logoutGroupOfUserUseCase(accessToken, idUser).collect {
                logoutGroupOfUserResponse.postValue(it)
            }
        }
    }

    val changeGroupResponse = MutableLiveData<Event<Boolean>>()
    fun changeIdGroup(accessToken: String, idUser: Int, idGroup: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            changeIdGroupGroupOfUserUseCase(accessToken, idUser, idGroup).collect {
                changeGroupResponse.postValue(it)
            }
        }
    }

    val changeNameResponse = MutableLiveData<Event<Boolean>>()
    fun changeName(accessToken: String, idUser: Int, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            changeNameGroupOfUserUseCase(accessToken, idUser, text).collect {
                changeNameResponse.postValue(it)
            }
        }
    }

    val userListResponse = MutableLiveData<Event<List<UserShort>>>()
    fun getUserList(accessToken: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userListGroupOfUserUseCase(accessToken, idUser).collect {
                userListResponse.postValue(it)
            }
        }
    }

    val getEntryLinkResponse = MutableLiveData<Event<EntryLink>>()
    fun getEntryLink(accessToken: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getEntryLinkGroupOfUserUseCase(accessToken, idUser).collect {
                getEntryLinkResponse.postValue(it)
            }
        }
    }

    val updateEntryLinkResponse = MutableLiveData<Event<EntryLink>>()
    fun updateEntryLink(accessToken: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateEntryLinkGroupOfUserUseCase(accessToken, idUser).collect {
                updateEntryLinkResponse.postValue(it)
            }
        }
    }

    val lockLinkResponse = MutableLiveData<Event<Boolean>>()
    fun lockEntryLink(accessToken: String, idUser: Int, state: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            lockEntryLinkGroupOfUserUseCase(accessToken, idUser, state).collect {
                lockLinkResponse.postValue(it)
            }
        }
    }

    val makeHeadResponse = MutableLiveData<Event<Boolean>>()
    fun makeHead(accessToken: String, idUser: Int, idSelUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            makeHeadGroupOfUserUseCase(accessToken, idUser, idSelUser).collect {
                makeHeadResponse.postValue(it)
            }
        }
    }

    val removeUserResponse = MutableLiveData<Event<Boolean>>()
    fun removeUser(accessToken: String, idUser: Int, idSelUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeUserGroupOfUserUseCase(accessToken, idUser, idSelUser).collect {
                removeUserResponse.postValue(it)
            }
        }
    }

    val groupOfUserResponse = MutableLiveData<Event<InviteData>>()
    fun getGroupOfUserUser(accessToken: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            groupOfUserUseCase(accessToken, idUser).collect {
                groupOfUserResponse.postValue(it)
            }
        }
    }

    fun changeGroupGOU(idGroup: Int, nameGroup: String){
        val authUser = dbUtils.getCurrentAuthUser()
        authUser!!.groupOfUser!!.idGroup = idGroup
        authUser.groupOfUser!!.nameGroup = nameGroup
        dbUtils.setCurrentAuthUser(authUser)
    }

    fun changeOlderUser(idUser: Int, nameUser: String, photo: String){
        val authUser = dbUtils.getCurrentAuthUser()
        authUser?.groupOfUser?.apply {
            idOlder = idUser
            userVeryShortModel!!.id = idUser
            userVeryShortModel!!.name = nameUser
            userVeryShortModel!!.photo = photo
            dbUtils.setCurrentAuthUser(authUser)
        }

    }
}