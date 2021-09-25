package com.example.myvoozkotlin.user.data

import android.util.Log
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.user.api.UserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
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

    override fun changeIdGroupUser(
        accessToken: String,
        idUser: Int,
        nameGroup: String,
        idGroup: Int
    ): Flow<Event<Boolean>> =
        flow<Event<Boolean>> {
            emit(Event.loading())
            val apiResponse = userApi.changeIdGroup(accessToken, idUser, idGroup)

            if (apiResponse.isSuccessful && apiResponse.body() != null){
                val authUserModel = dbUtils.getCurrentAuthUser()
                if(authUserModel != null){
                    authUserModel.idGroup = idGroup
                    authUserModel.nameGroup = nameGroup
                    dbUtils.setCurrentAuthUser(authUserModel)
                }
                emit(Event.success(apiResponse.body()!!))
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

    override fun getEmptyAuditory(
        date: String,
        idCorpus: Int,
        lowNumber: Int,
        upperNumber: Int,
        idUniversity: Int
    ): Flow<Event<List<List<Classroom>>>> =
        flow<Event<List<List<Classroom>>>> {
            emit(Event.loading())
            val apiResponse = userApi.getEmptyAuditory(date, idCorpus, lowNumber, upperNumber, idUniversity)

            if (apiResponse.isSuccessful && apiResponse.body() != null){
                emit(Event.success(apiResponse.body()!!))
            }
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun getNameUniversity(): String {
        return BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, "Не выбрано")!!
    }
}