package com.example.myvoozkotlin.searchEmptyAuditory.model

import com.example.myvoozkotlin.models.PhotoItem
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Classroom(
    @SerializedName("name") var name:String,
    @SerializedName("floor") var floor: Int
    ) : Serializable