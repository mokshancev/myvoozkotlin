package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName


data class InviteData(
        @SerializedName("infoGroup") var infoGroup:InfoGroup,
        @SerializedName("error") var error:Int,
        @SerializedName("groupOfUser") val groupOfUser:GroupOfUser
    )