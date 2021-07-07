package com.example.myvoozkotlin.home.domain

import com.example.homelibrary.model.News
import com.example.myvoozkotlin.helpers.Event

interface INewsUseCase {
    operator fun invoke(id_group: Int): Event<MutableList<News>>
}