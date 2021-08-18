package com.example.myvoozkotlin.auth.data

import android.util.Log
import com.example.homelibrary.model.*
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.data.api.AuthApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.data.db.realmModels.UserVeryShortModel
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    var realm: Realm,
    var dbUtils: DbUtils,
    private val authApi: AuthApi
) : AuthRepository {
    override fun authVk(
        accessToken: String,
        idUser: Int,
        idUniversity: Int,
        idGroup: Int,
        keyNotification: String
    ): Flow<Event<AuthUser>> =
        flow<Event<AuthUser>> {
            emit(Event.loading())
            val apiResponse = authApi.authVk(accessToken, idUser, idUniversity, idGroup, keyNotification)

            if (apiResponse.isSuccessful && apiResponse.body() == null)
            {
                emit(Event.error("lol"))
            }
            else
            {
                val authUser = apiResponse.body()!!
                val authUserModel = AuthUserModel()
                authUserModel.accessToken = authUser.accessToken
                authUserModel.photo = authUser.photo
                authUserModel.firstName = authUser.firstName
                authUserModel.lastName = authUser.lastName
                authUserModel.idRank = authUser.idRank
                authUserModel.idUniversity = authUser.idUniversity
                authUserModel.id = authUser.id
                authUserModel.nameUniversity = authUser.nameUniversity
                authUserModel.idGroup = authUser.idGroup
                authUserModel.nameGroup = authUser.nameGroup
                authUserModel.idGroupOfUser = authUser.idGroupOfUser

                val groupOfUserModel = GroupOfUserModel()
                groupOfUserModel.id = authUser.groupOfUser.id
                groupOfUserModel.idCreator = authUser.groupOfUser.idCreator
                groupOfUserModel.idOlder = authUser.groupOfUser.idOlder
                groupOfUserModel.idGroup = authUser.groupOfUser.idGroup
                groupOfUserModel.countUsers = authUser.groupOfUser.countUsers
                groupOfUserModel.nameUniversity = authUser.groupOfUser.nameUniversity
                groupOfUserModel.idUniversity = authUser.groupOfUser.idUniversity
                groupOfUserModel.nameGroup = authUser.groupOfUser.nameGroup
                groupOfUserModel.name = authUser.groupOfUser.name
                groupOfUserModel.image = authUser.groupOfUser.image

                val userVeryShortModel = UserVeryShortModel()
                userVeryShortModel.id = authUser.groupOfUser.userVeryShort.id
                userVeryShortModel.name = authUser.groupOfUser.userVeryShort.name
                userVeryShortModel.photo = authUser.groupOfUser.userVeryShort.photo
                groupOfUserModel.userVeryShortModel = userVeryShortModel

                authUserModel.groupOfUser = groupOfUserModel
                dbUtils.setCurrentAuthUser(authUserModel)

                emit(Event.success(apiResponse.body()!!))

                if(authUser.groupOfUser.id != 0){
                    BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.GROUP_AUTORIZATE.ordinal).apply()
                }
                else{
                    BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.AUTORIZATE.ordinal).apply()
                }
                BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_USER_GROUP_ID, authUser.idGroup).apply()
                BaseApp.getSharedPref().edit().putString(Constants.APP_PREFERENCES_USER_GROUP_NAME, authUser.nameGroup).apply()
                BaseApp.getSharedPref().edit().putString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, authUser.nameUniversity).apply()
                BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, authUser.idUniversity).apply()
            }
        }.catch { e ->
            Log.d("superror", e.toString())
            emit(Event.error("lol2"))
            e.printStackTrace()
        }
}