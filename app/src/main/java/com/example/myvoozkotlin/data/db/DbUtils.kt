package com.example.myvoozkotlin.data.db

import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.home.model.LessonDbModel
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

    fun getAllSchedule(): List<LessonDbModel>? {
        val authUserModel = realm.where(LessonDbModel::class.java)
            .findAll()
        return if (authUserModel == null) {
            null
        } else realm.copyFromRealm(authUserModel)
    }

    fun setCurrentAuthUser(authUserModel: AuthUserModel) {
        realm.executeTransaction { it.insertOrUpdate(authUserModel) }
    }

    fun addSchedule(lessonDbModel: LessonDbModel) {
        realm.executeTransaction { it.insertOrUpdate(lessonDbModel) }
    }

    fun removeAllSchedule() {
        val entity = realm.where(LessonDbModel::class.java)
        realm.executeTransaction {
            entity?.findAll()?.deleteAllFromRealm()
        }
    }

    fun removeCurrentUser() {
        val entity = realm.where(AuthUserModel::class.java).findFirst()
        realm.executeTransaction {
            entity?.deleteFromRealm()
        }
    }
}