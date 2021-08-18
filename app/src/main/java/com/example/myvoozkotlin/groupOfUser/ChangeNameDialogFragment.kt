package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentChangeGroupOfUserNameBinding
import com.example.myvoozkotlin.databinding.DialogFragmentChangeUserFullnameBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeNameDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentChangeGroupOfUserNameBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
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
        _binding = DialogFragmentChangeGroupOfUserNameBinding.inflate(inflater, container, false)

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
            etName.setText(authUserModel.groupOfUser!!.name)
        }
    }

    private fun initObservers() {
        observeOnNewsResponse()
    }

    private fun observeOnNewsResponse() {
        groupOfUserViewModel.changeNameResponse.observe(viewLifecycleOwner, Observer {
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
                binding.etName.text.length !in 1..32 ->
                    UtilsUI.makeToast(getString(R.string.toast_gou_name))
                else ->{
                    groupOfUserViewModel.changeName(authUserModel.accessToken, authUserModel.id, binding.etName.text.toString())
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