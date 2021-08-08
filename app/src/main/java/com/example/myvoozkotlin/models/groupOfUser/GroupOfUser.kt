package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import kotlinx.serialization.SerialName

data class GroupOfUser(
        @SerializedName("id_creator") val idCreator:Int,
        @SerializedName("id_older") val idOlder:Int,
        @SerializedName("id_group") val idGroup: Int,
        @SerializedName("id") val id:Int,
        @SerializedName("count_users") val countUsers:Int,
        @SerializedName("name") val name:String,
        @SerializedName("image") val image:String,
        @SerializedName("userVeryShort") val userVeryShort:UserVeryShort,
    )