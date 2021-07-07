package com.example.myvoozkotlin.domain.interactors

import com.example.myvoozkotlin.home.domain.INewsRepository

class LoadNews(private val repository: INewsRepository) {
    suspend fun invoke(idGroup: Int) = repository.loadNews(idGroup)
}