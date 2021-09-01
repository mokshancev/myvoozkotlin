package com.example.myvoozkotlin.aboutNew.data

import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.aboutNew.domain.OnBoardingRepository
import com.example.myvoozkotlin.aboutNew.model.OnBoardingPage

class OnBoardingRepositoryImpl : OnBoardingRepository {
    companion object {
        const val CONSTANT_IS_FIRST_SEEN = "aboutNewIsFirstSeen"
        private const val CONSTANT_IS_SHOW_ABOUT_NEW_BLOCK = "isShowAboutNewBlock"
    }

    override fun isFirstSeen(): Boolean {
        return BaseApp.getSharedPref().getBoolean(CONSTANT_IS_FIRST_SEEN, true)
    }

    override fun setFirstSeen(isFirst: Boolean) {
        BaseApp.getSharedPref().edit().putBoolean(CONSTANT_IS_FIRST_SEEN, isFirst).apply()
    }

    override fun isShowAboutNewFragment(): Boolean {
        return BaseApp.getSharedPref().getBoolean(CONSTANT_IS_SHOW_ABOUT_NEW_BLOCK, true)
    }

    override fun setShowAboutNewFragment(isShow: Boolean) {
        BaseApp.getSharedPref().edit().putBoolean(CONSTANT_IS_SHOW_ABOUT_NEW_BLOCK, isShow).apply()
    }

    override fun getBoards(): List<OnBoardingPage> {
        return mutableListOf(
            OnBoardingPage(R.string.slide_about_new_title_0,
            R.string.slide_about_new_posttitle_0,
            R.drawable.slide_about_new_0),
            OnBoardingPage(R.string.slide_about_new_title_1,
            R.string.slide_about_new_posttitle_1,
            R.drawable.slide_about_new_1),
            OnBoardingPage(R.string.slide_about_new_title_2,
                R.string.slide_about_new_posttitle_2,
                R.drawable.slide_about_new_2),
            OnBoardingPage(R.string.slide_about_new_title_3,
                R.string.slide_about_new_posttitle_3,
                R.drawable.slide_about_new_3)
        )
    }
}