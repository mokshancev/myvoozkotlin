package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Lesson(
        @SerializedName("name") val name:String,
        @SerializedName("name_type") val typeName: String,
        @SerializedName("classroom") val classroom:String,
        @SerializedName("name_teacher") val teacher: String,
        @SerializedName("time_f") val firstTime: String,
        @SerializedName("time_l") val lastTime: String,
        @SerializedName("number") val number: Int
    )