package com.example.myvoozkotlin.notification.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentNotificationBinding
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.models.TabItem
import com.example.myvoozkotlin.notification.adapter.NotificationViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment: BaseFragment() {
    private val userViewModel: UserViewModel by viewModels()
    private var authUserModel: AuthUserModel? = null

    private val tabItems = mutableListOf<TabItem>()
    private var authorizationState: AuthorizationState? = null

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
    }

    private fun configureViews() {
        authUserModel = userViewModel.getCurrentAuthUser()
        authorizationState = Utils.getAuthorisationState(authUserModel)
        initData()
        initToolbar()
        initObservers()
        configureViewPager()
    }

    private fun configureViewPager(){
        val adapter = NotificationViewPagerAdapter(requireActivity(), tabItems)
        binding.vpNotification.adapter = adapter
        TabLayoutMediator(
            binding.tlTypesNotification, binding.vpNotification
        ) { tab, position -> tab.text = tabItems[position].name }.attach()
    }

    private fun initData(){
        if(authorizationState != AuthorizationState.UNAUTORIZATE){
            tabItems.add(TabItem("Все", 0, true))
        }
        tabItems.add(TabItem("Университет", 1, false))
        if(authorizationState == AuthorizationState.GROUP_AUTORIZATE){
            tabItems.add(TabItem("Группа", 2, false))
        }
        if(authorizationState != AuthorizationState.UNAUTORIZATE){
            tabItems.add(TabItem("Лично мне", 3, false))
        }
    }

    private fun setListeners(){

    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_notification)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initObservers() {
        observeOnAuthUserChangeResponse()
    }

    private fun observeOnAuthUserChangeResponse() {

    }
}