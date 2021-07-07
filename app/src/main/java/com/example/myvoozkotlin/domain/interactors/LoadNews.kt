package com.example.myvoozkotlin.domain.interactors

import com.example.myvoozkotlin.home.domain.NewsRepository

class LoadNews(private val repository: NewsRepository) {
    suspend fun invoke(idGroup: Int) = repository.loadNews(idGroup)
}