package com.example.myvoozkotlin.data.db.realmModels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.SerialName


open class UserVeryShortModel(
        @PrimaryKey
        var id:Int = 0,
        var name:String = "",
        var photo: String = "",
    ): RealmObject()