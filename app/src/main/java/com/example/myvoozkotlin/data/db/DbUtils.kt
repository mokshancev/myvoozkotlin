package com.example.myvoozkotlin.data.db

import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import io.realm.Realm
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val realm: Realm
) {

    fun getCurrentAuthUser(): AuthUserModel {
        var contractModel = realm.where(AuthUserModel::class.java)
            .findFirst()
        return if (contractModel == null) {
            contractModel = AuthUserModel()
            contractModel
        } else realm.copyFromRealm(contractModel)
    }

    fun setCurrentAuthUser(authUserModel: AuthUserModel) {
        realm.executeTransaction { it.insertOrUpdate(authUserModel) }
    }
}