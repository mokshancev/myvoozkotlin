package com.example.myvoozkotlin.user.data

import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.data.api.UserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.helpers.*
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
                if(authUserModel != null){
                    authUserModel.firstName = firstName
                    authUserModel.lastName = secondName
                    dbUtils.setCurrentAuthUser(authUserModel)
                }
            }
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun getIdUniversity(): Int {
        return BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)
    }

    override fun getNameGroup(): String {
        return BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_GROUP_NAME, "Не выбрано")!!
    }

    override fun getIdGroup(): Int {
        return BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0)
    }

    override fun getNameUniversity(): String {
        return BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, "Не выбрано")!!
    }
}