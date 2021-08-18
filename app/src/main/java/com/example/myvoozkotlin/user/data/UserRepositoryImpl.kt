package com.example.myvoozkotlin.user.data

import android.util.Log
import com.example.homelibrary.model.*
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.data.api.AuthApi
import com.example.myvoozkotlin.data.api.UserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.data.db.realmModels.UserVeryShortModel
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.user.domain.UserRepository
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    var realm: Realm,
    var dbUtils: DbUtils,
    private val userApi: UserApi
) : UserRepository {
    override fun changeUserFullName(
        accessToken: String,
        idUser: Int,
        firstName: String,
        secondName: String
    ): Flow<Event<Boolean>> =
        flow<Event<Boolean>> {
            emit(Event.loading())
            val apiResponse = userApi.changeFullName(accessToken, idUser, firstName, secondName)

            if (apiResponse.isSuccessful && apiResponse.body() != null){
                emit(Event.success(apiResponse.body()!!))
                val authUserModel = dbUtils.getCurrentAuthUser()
                authUserModel.firstName = firstName
                authUserModel.lastName = secondName
                dbUtils.setCurrentAuthUser(authUserModel)
            }
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun changeAuthUserListener(onAuthUserChange: OnAuthUserChange?){
        realm.where(AuthUserModel::class.java)
            .findAll()
            .asChangesetObservable()

            .subscribe {
                onAuthUserChange?.onAuthUserChange()
            }
    }
}