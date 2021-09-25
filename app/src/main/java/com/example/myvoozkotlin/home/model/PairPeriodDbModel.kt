package com.example.myvoozkotlin.home.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


open class PairPeriodDbModel(
        @PrimaryKey
        var number: Int = 0,
        var firstTime: String = "",
        var lastTime: String = "",
    ): RealmObject(), RealmModel