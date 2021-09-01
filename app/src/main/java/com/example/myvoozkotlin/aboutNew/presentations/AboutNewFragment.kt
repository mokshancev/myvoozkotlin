package com.example.myvoozkotlin.aboutNew.presentations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myvoozkotlin.R
import com.google.android.material.tabs.TabLayoutMediator
import com.example.myvoozkotlin.aboutNew.adapter.SliderAdapter
import com.example.myvoozkotlin.aboutNew.presentations.viewModel.OnBoardingViewModel
import com.example.myvoozkotlin.databinding.FragmentAboutNewBinding
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.navigation.navigator

class AboutNewFragment : Fragment() {

    private var _binding: FragmentAboutNewBinding? = null
    private val binding get() = _binding!!
    private var sliderAdapter: SliderAdapter? = null
    private var onBoardingViewModel: OnBoardingViewModel? = null

    companion object {
        fun newInstance(): AboutNewFragment {
            return AboutNewFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingViewModel = ViewModelProvider(this).get(OnBoardingViewModel::class.java)
        setListeners()
        initSlideAdapter()
        initTabLayout()
        setPaddingTopMenu()
    }

    private fun setPaddingTopMenu() {
        binding.root.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tlAboutNew, binding.vpAboutNew) { _, _ -> }.attach()
    }

    private fun initSlideAdapter() {
        sliderAdapter = onBoardingViewModel?.let { SliderAdapter(it.getBoards()) }
        binding.vpAboutNew.adapter = sliderAdapter
    }

    private fun setListeners() {
        binding.cvCloseButton.setOnClickListener {
            closeFragment()
        }

        binding.cvNextButton.setOnClickListener {
            if (hasNextPage())
                setNextPage()
            else
                closeFragment()
        }
    }

    private fun hasNextPage():Boolean{
        return binding.vpAboutNew.currentItem + 1 < binding.tlAboutNew.tabCount
    }

    private fun setNextPage(){
        binding.vpAboutNew.setCurrentItem(binding.vpAboutNew.currentItem.plus(1), true)
    }

    private fun closeFragment() {
        navigator().showSelectGroupScreen(R.id.activityMainContainer,
            isFirst = true,
            isBackStack = false
        )
        onBoardingViewModel!!.setFirstSeen(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}