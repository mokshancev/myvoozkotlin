package com.example.myvoozkotlin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentChangeUserFullnameBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFullNameDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentChangeUserFullnameBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var authUserModel: AuthUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogFragmentChangeUserFullnameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authUserModel = userViewModel.getCurrentAuthUser()
        configureViews()
        initObservers()
        setListeners()
    }

    private fun configureViews() {
        binding.apply {
            etFirstName.setText(authUserModel.firstName)
            etSecondName.setText(authUserModel.lastName)
        }
    }

    private fun initObservers() {
        observeOnNewsResponse()
    }

    private fun observeOnNewsResponse() {
        userViewModel.changeFullNameResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    setLoadStateBtn()
                }
                Status.SUCCESS -> {
                    setOpenStateBtn()

                    if (it.data == null) {

                    } else {
                        dismiss()
                    }
                }
                Status.ERROR -> {
                    //binding.progressBar.hide()
                }
            }
        })
    }

    private fun setListeners() {
        binding.cvSaveButton.setOnClickListener {
            when {
                binding.etFirstName.text.length !in 1..32 ->
                    UtilsUI.makeToast(getString(R.string.toast_user_name))
                binding.etSecondName.text.length !in 1..32 ->
                    UtilsUI.makeToast(getString(R.string.toast_user_second_name))
                else ->{
                    userViewModel.changeFullName(authUserModel.accessToken, authUserModel.id, binding.etFirstName.text.toString(), binding.etSecondName.text.toString())
                }
            }
        }
    }

    private fun setLoadStateBtn(){
        binding.ivLoading.show()
        val rotationAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_infinity)
        binding.ivLoading.startAnimation(rotationAnimation)
        binding.cvSaveButton.isEnabled = false
    }

    private fun setOpenStateBtn(){
        binding.ivLoading.hide()
        binding.ivLoading.clearAnimation()
        binding.cvSaveButton.isEnabled = true
    }
}