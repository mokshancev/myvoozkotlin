package com.example.myvoozkotlin

import android.app.Application
import com.example.myvoozkotlin.domain.framework.Interactors
import com.example.myvoozkotlin.domain.interactors.LoadNews
import com.example.myvoozkotlin.domain.repository.RemoteDataSourceImpl
import com.example.myvoozkotlin.home.data.NewsRepository

class BaseApp: Application() {

    lateinit var interactors: Interactors

    companion object{
        var instance: BaseApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //val newsRepository = NewsRepository(RemoteDataSourceImpl())
        //interactors = Interactors(LoadNews(newsRepository))
    }
}