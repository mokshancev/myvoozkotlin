package com.example.myvoozkotlin.domain.repository

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.domain.NetworkService

class RemoteDataSourceImpl: RemoteDataSource {

    var newsApi = NetworkService.newsRetrofitService()

    override suspend fun loadNewsByGroup(idGroup: Int): MutableList<News> {
        return newsApi.loadNews(idGroup)
    }

}