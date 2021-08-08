package com.example.homelibrary.model

import kotlinx.serialization.SerialName


data class UserVeryShort(
        @SerialName("name") val name:String,
        val photo: String,
        @SerialName("id") val id:Int,
    )