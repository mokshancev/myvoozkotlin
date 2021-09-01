package com.example.myvoozkotlin.leftMenu.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentChangeUserFullnameBinding
import com.example.myvoozkotlin.databinding.DialogFragmentConfirmLogoutBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmLogoutDialogFragment: DialogFragment() {
    private var _binding: DialogFragmentConfirmLogoutBinding? = null
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
        _binding = DialogFragmentConfirmLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        setCancelClickListener()
        setLogoutClickListener()
    }

    private fun setCancelClickListener(){
        binding.cvCancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setLogoutClickListener(){
        binding.cvLogoutButton.setOnClickListener {
            userViewModel.removeCurrentUser()
            dismiss()
        }
    }
}