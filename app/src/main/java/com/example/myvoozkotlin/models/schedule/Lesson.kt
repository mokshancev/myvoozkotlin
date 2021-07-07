package com.example.homelibrary.model

import kotlinx.serialization.SerialName


data class Lesson(
        @SerialName("name") val name:String,
        @SerialName("type") val type: String,
        @SerialName("classroom") val classroom:String,
        @SerialName("teacher") val teacher: String,
        @SerialName("firstTime") val firstTime:String,
        @SerialName("lastTime") val lastTime:String,
        @SerialName("number") val number: Int
    )