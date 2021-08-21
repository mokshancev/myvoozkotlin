package com.example.myvoozkotlin.models.news

import com.google.gson.annotations.SerializedName

class News(val image:String,
           val title:String,
           @SerializedName("logo_image")
           val logoImage: String
           , val name: String, val stories: MutableList<Story>, val link: String) {
}