package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName


data class UserShort(
    @SerializedName("first_name") val firstName:String,
    @SerializedName("last_name") val lastName:String,
    @SerializedName("date") val date:String,
    val photo: String,
    @SerializedName("id") val id:Int,
    @SerializedName("id_user_select") val idUserSelect:Int,
    @SerializedName("check") var check: Boolean = false,
    )