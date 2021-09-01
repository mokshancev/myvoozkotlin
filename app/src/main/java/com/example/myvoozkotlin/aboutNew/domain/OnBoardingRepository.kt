package com.example.myvoozkotlin.aboutNew.domain

import com.example.myvoozkotlin.aboutNew.model.OnBoardingPage

interface OnBoardingRepository {
    fun isFirstSeen(): Boolean
    fun setFirstSeen(isFirst: Boolean)
    fun isShowAboutNewFragment(): Boolean
    fun setShowAboutNewFragment(isShow: Boolean)
    fun getBoards(): List<OnBoardingPage>
}