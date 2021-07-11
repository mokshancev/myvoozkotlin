package com.example.myvoozkotlin.home.helpers

import omari.hamza.storyview.model.MyStory

interface OnStoryClick {
    fun onStoryClick(stories: List<MyStory>)
}