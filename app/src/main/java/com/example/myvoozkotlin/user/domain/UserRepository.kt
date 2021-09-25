package com.example.myvoozkotlin.user.domain

import android.graphics.Bitmap
import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface UserRepository {
    fun changeUserFullName(accessToken: String, idUser : Int, firstName : String, secondName : String): Flow<Event<Boolean>>
    fun changeIdGroupUser(accessToken: String, idUser: Int, nameGroup: String, idGroup: Int): Flow<Event<Boolean>>
    fun getIdUniversity(): Int
    fun getNameGroup(): String
    fun getIdGroup(): Int
    fun getEmptyAuditory(date: String, idCorpus: Int, lowNumber: Int, upperNumber: Int, idUniversity: Int): Flow<Event<List<List<Classroom>>>>
    fun getNameUniversity(): String
}