package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName


data class InfoGroup(
        @SerializedName("name_university") var nameUniversity:String,
        @SerializedName("name_group") val nameGroup:String,
        @SerializedName("id_university") val idUniversity:Int,
        @SerializedName("id_group") val idGroup:Int,
    )