package com.example.myvoozkotlin.home.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Lesson(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name:String,
        @SerializedName("name_type") val typeName: String,
        @SerializedName("classroom") val classroom:String,
        @SerializedName("name_teacher") val teacher: String,
        @SerializedName("time_f") val firstTime: String,
        @SerializedName("time_l") val lastTime: String,
        @SerializedName("number") val number: Int,
        @SerializedName("min_week") val minWeek: Int,
        @SerializedName("max_week") val maxWeek: Int,
        @SerializedName("wday") val dayOfWeek: Int,
    )