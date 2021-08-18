package com.example.myvoozkotlin.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.MainFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding
import com.example.myvoozkotlin.databinding.FragmentSplashBinding
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum


class SplashFragment : BaseFragment() {

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var authUserModel: AuthUserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        initObservers()



        authUserModel = userViewModel.getCurrentAuthUser()

        if(authUserModel == null){
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.activityMainContainer, MainFragment(), MainFragment().javaClass.simpleName)
                .addToBackStack(null)
                .commit()
        }
        else{
            authViewModel.authVk(authUserModel.accessToken
                , authUserModel.id, authUserModel.idUniversity, authUserModel.idGroup, "s")
            //todo add notification accessToken
        }
    }

    private fun configureViews(){

    }

    private fun initObservers() {
        observeOnAuthResponse()
    }

    private fun observeOnAuthResponse() {
        authViewModel.authVkResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.activityMainContainer, MainFragment(), MainFragment().javaClass.simpleName)
                            .addToBackStack(null)
                            .commit()
                    }
                }
                Status.ERROR -> {
                    UtilsUI.makeToast("error auth")
                }
            }
        })
    }
}