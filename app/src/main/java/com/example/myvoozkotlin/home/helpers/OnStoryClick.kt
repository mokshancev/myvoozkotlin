package com.example.myvoozkotlin.home.helpers

import com.example.myvoozkotlin.models.news.News
import omari.hamza.storyview.model.MyStory

interface OnStoryClick {
    fun onStoryClick(stories: News)
}