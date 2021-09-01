package com.example.myvoozkotlin.aboutNew.presentations.viewModel

import androidx.lifecycle.ViewModel
import com.example.myvoozkotlin.aboutNew.data.OnBoardingRepositoryImpl
import com.example.myvoozkotlin.aboutNew.model.OnBoardingPage
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
) : ViewModel() {
    private val onBoardingRepository = OnBoardingRepositoryImpl()
    fun isFirstSeen() : Boolean {
        return onBoardingRepository.isFirstSeen()
    }

    fun setFirstSeen(isFirst: Boolean) {
        onBoardingRepository.setFirstSeen(isFirst)
    }

    fun isShowAboutNewFragment() : Boolean {
        return onBoardingRepository.isShowAboutNewFragment()
    }

    fun setShowAboutNewFragment(isShow: Boolean) {
        onBoardingRepository.setShowAboutNewFragment(isShow)
    }

    fun getBoards(): List<OnBoardingPage>{
        return onBoardingRepository.getBoards()
    }
}