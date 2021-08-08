package com.example.myvoozkotlin.data.db

import com.example.myvoozkotlin.BaseApp
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmModel

class RealmUtils private constructor() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface RealmUtilsEntryPoint {
        fun provideRealm(): Realm
    }

    private var realm: Realm

    init {
        val appContext = BaseApp.instance?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, RealmUtilsEntryPoint::class.java)

        realm = hiltEntryPoint.provideRealm()
    }

    companion object {
        var instance: RealmUtils = RealmUtils()
    }

    fun insertOrUpdate(realmObject: RealmModel) {
        if (!realm.isInTransaction)
            realm.beginTransaction()
        realm.insertOrUpdate(realmObject)
        realm.commitTransaction()
    }

    fun insertOrUpdate(objects: Collection<RealmModel>) {
        if (!realm.isInTransaction)
            realm.beginTransaction()
        realm.insertOrUpdate(objects)
        realm.commitTransaction()
    }

    fun copyToRealmOrUpdate(realmObject: RealmModel) {
        if (!realm.isInTransaction)
            realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmObject)
        realm.commitTransaction()
    }

    fun copyToRealmOrUpdate(objects: Collection<RealmModel?>?) {
        if (!realm.isInTransaction)
            realm.beginTransaction()
        realm.copyToRealmOrUpdate(objects)
        realm.commitTransaction()
    }
}