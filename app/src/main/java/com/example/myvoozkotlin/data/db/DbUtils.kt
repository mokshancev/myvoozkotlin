package com.example.myvoozkotlin.data.db

import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import io.realm.Realm
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val realm: Realm
) {

    fun getCurrentAuthUser(): AuthUserModel? {
        val authUserModel = realm.where(AuthUserModel::class.java)
            .findFirst()
        return if (authUserModel == null) {
            null
        } else realm.copyFromRealm(authUserModel)
    }

    fun setCurrentAuthUser(authUserModel: AuthUserModel) {
        realm.executeTransaction { it.insertOrUpdate(authUserModel) }
    }

    fun removeCurrentUser() {
        val entity = realm.where(AuthUserModel::class.java).findFirst()
        realm.executeTransaction {
            entity?.deleteFromRealm()
        }
    }
}