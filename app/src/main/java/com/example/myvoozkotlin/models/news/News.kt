package com.example.myvoozkotlin.models.news

class News(val image:String, val title:String, val logoImage: String
           , val name: String, val stories: MutableList<Story>, val url: String) {
}