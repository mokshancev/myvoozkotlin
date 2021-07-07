package com.example.myvoozkotlin.home.domain

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.helpers.Event

interface INewsRepository {
    fun loadNews(idGroup: Int): Event<MutableList<News>>
}