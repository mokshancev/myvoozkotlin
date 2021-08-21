package com.example.myvoozkotlin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentChangeUserFullnameBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFullNameDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentChangeUserFullnameBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentChangeUserFullnameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews()
        initObservers()
        setListeners()
    }

    private fun configureViews() {
        binding.apply {
            getCurrentUser()?.let {
                etFirstName.setText(it.firstName)
                etSecondName.setText(it.lastName)
            }
        }
    }

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun initObservers() {
        observeOnChangeFullNameResponse()
    }

    private fun observeOnChangeFullNameResponse() {
        userViewModel.changeFullNameResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    if (it.data != null) {
                        dismiss()
                    }
                }
                Status.ERROR -> {
                    (requireActivity() as MainActivity).showWait(false)
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
                    getCurrentUser()?.let {
                        userViewModel.changeFullName(it.accessToken, it.id, binding.etFirstName.text.toString(), binding.etSecondName.text.toString())
                    }
                }
            }
        }
    }
}