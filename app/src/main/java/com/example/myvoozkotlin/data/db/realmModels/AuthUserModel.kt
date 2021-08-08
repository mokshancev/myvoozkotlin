package com.example.myvoozkotlin.data.db.realmModels

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class AuthUserModel(
        @PrimaryKey
        var id: Int = 0,
        var accessToken:String = "",
        var photo: String = "",
        var firstName:String = "",
        var lastName: String = "",
        var idRank:String = "",
        var idUniversity:Int = 0,
        var nameUniversity:String = "",
        var idGroup:Int = 0,
        var nameGroup:String = "",
        var idGroupOfUser:Int = 0,
        var groupOfUser: GroupOfUserModel? = null
): RealmObject(), RealmModel