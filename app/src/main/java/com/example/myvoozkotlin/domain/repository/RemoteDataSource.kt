package com.example.myvoozkotlin.domain.repository

import com.example.homelibrary.model.News

interface RemoteDataSource {
    suspend fun loadNewsByGroup(idGroup: Int): MutableList<News>
}