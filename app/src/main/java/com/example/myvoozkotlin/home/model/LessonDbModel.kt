package com.example.myvoozkotlin.home.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


open class LessonDbModel(
        @PrimaryKey
        var id: Int = 0,
        var name:String = "",
        var typeName: String = "",
        var classroom:String = "",
        var teacher: String = "",
        var firstTime: String = "",
        var lastTime: String = "",
        var number: Int = 0,
        var minWeek: Int = 0,
        var maxWeek: Int = 0,
        var dayOfWeek: Int = 0,
    ): RealmObject(), RealmModel