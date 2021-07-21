package com.example.myvoozkotlin.search.data

import android.util.Log
import com.example.myvoozkotlin.data.api.SearchApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.models.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {

    override fun loadUniversityList(text: String): Flow<Event<List<SearchItem>>> =
        flow<Event<List<SearchItem>>> {
            emit(Event.loading())
            val apiResponse = searchApi.loadUniversityList(text)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun loadGroupList(text: String, idUniversity: Int): Flow<Event<List<SearchItem>>> =
        flow<Event<List<SearchItem>>> {
            emit(Event.loading())
            val apiResponse = searchApi.loadGroupList(text, idUniversity)

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