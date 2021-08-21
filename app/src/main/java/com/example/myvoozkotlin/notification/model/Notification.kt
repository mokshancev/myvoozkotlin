package com.example.homelibrary.model

import com.example.myvoozkotlin.models.PhotoItem
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import kotlinx.serialization.SerialName

data class Notification(
        @SerializedName("photo") val photo:String,
        @SerializedName("title") val title:String,
        @SerializedName("text") val text: String,
        @SerializedName("link") val link: String,
        @SerializedName("date") val date: String,
        @SerializedName("images") val images: List<PhotoItem>
    )