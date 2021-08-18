package com.example.myvoozkotlin.data.db.realmModels

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.SerialName

open class GroupOfUserModel(
        @PrimaryKey
        var id:Int = 0,
        var idCreator:Int = 0,
        var idOlder:Int = 0,
        var idGroup: Int = 0,
        var nameGroup: String = "",
        var idUniversity: Int = 0,
        var nameUniversity: String = "",
        var countUsers:Int = 0,
        var name:String = "",
        var image:String = "",
        var userVeryShortModel:UserVeryShortModel? = null
    ): RealmObject()