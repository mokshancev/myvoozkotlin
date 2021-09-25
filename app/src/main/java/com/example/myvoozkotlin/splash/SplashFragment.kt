package com.example.myvoozkotlin.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.aboutNew.presentations.viewModel.OnBoardingViewModel
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentSplashBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.home.viewModels.ScheduleViewModel
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        if (onBoardingViewModel.isFirstSeen()){
            navigator().showAboutNewScreen()
        }
        else{
            if(userViewModel.getIdGroup() == 0){
                navigator().showSelectGroupScreen(R.id.activityMainContainer, true,
                    isBackStack = false
                )
            }
            else{
                if(getCurrentUser() == null){
                    navigator().showMainScreen()
                }
                else{
                    getCurrentUser()?.let {
                        Log.d("authUserInfo", it.accessToken + " " + it.id)
                        authViewModel.authVk(it.accessToken, it.id, it.idUniversity, it.idGroup, FirebaseInstanceId.getInstance().token!!)
                    }
                }
            }
        }
    }

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun initObservers() {
        observeOnAuthResponse()
        //observeOnLoadAllScheduleResponse()
    }

    private fun observeOnAuthResponse() {
        authViewModel.authVkResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if (it.data == null) {
                        userViewModel.removeCurrentUser()
                        navigator().showMainScreen()
                    }
                    else{
                        navigator().showMainScreen()
//                        getCurrentUser()?.apply {
//                            scheduleViewModel.loadAllSchedule(accessToken, id)
//                        }
                    }
                }
                Status.ERROR -> {
                    userViewModel.removeCurrentUser()
                    navigator().showMainScreen()
                }
            }
        })
    }

//    private fun observeOnLoadAllScheduleResponse() {
//        scheduleViewModel.allScheduleResponse.observe(viewLifecycleOwner, {
//            when (it.status) {
//                Status.LOADING -> {
//                    scheduleViewModel.removeAllSchedule()
//                    binding.tvStateLoad.text = "Обновляю расписание"
//                }
//                Status.SUCCESS -> {
//                    navigator().showMainScreen()
//                }
//                Status.ERROR -> {
//                    navigator().showMainScreen()
//                }
//            }
//        })
//    }
}