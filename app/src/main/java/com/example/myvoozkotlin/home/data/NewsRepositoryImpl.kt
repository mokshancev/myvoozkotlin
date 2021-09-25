package com.example.myvoozkotlin.home.data

import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.home.api.NewsApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override fun loadNews(idGroup: Int): Flow<Event<List<News>>> =
        flow<Event<List<News>>> {
            emit(Event.loading())
            val apiResponse = newsApi.loadNews(idGroup)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }
}