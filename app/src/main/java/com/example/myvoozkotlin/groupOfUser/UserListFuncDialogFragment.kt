package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentGouUserFuncBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFuncDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentGouUserFuncBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private lateinit var authUserModel: AuthUserModel
    private var idUser = 0

    companion object {
        const val CONSTANT_ID_USER = "id_user"
        const val REQUEST_MAKE_HEAD = "search_response_make_head"
        const val REQUEST_REMOVE_USER = "search_response_remove_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogFragmentGouUserFuncBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idUser = requireArguments().getInt(CONSTANT_ID_USER)

        authUserModel = userViewModel.getCurrentAuthUser()
        configureViews()
        initObservers()
        setListeners()
    }

    private fun configureViews() {

    }

    private fun initObservers() {
        observeOnMakeHeadResponse()
        observeOnRemoveUserResponse()
    }

    private fun observeOnMakeHeadResponse() {
        groupOfUserViewModel.makeHeadResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        (requireActivity() as MainActivity).showWait(false)

                        val bundle = Bundle()
                        bundle.putInt(CONSTANT_ID_USER, idUser)
                        parentFragmentManager.setFragmentResult(REQUEST_MAKE_HEAD, bundle)
                        dismiss()
                    }
                }
                Status.ERROR -> {
                    //binding.progressBar.hide()
                }
            }
        })
    }

    private fun observeOnRemoveUserResponse() {
        groupOfUserViewModel.removeUserResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        (requireActivity() as MainActivity).showWait(false)
                        val bundle = Bundle()
                        bundle.putInt(CONSTANT_ID_USER, idUser)
                        parentFragmentManager.setFragmentResult(REQUEST_REMOVE_USER, bundle)
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
        binding.rlMakeHeadBtn.setOnClickListener {
            groupOfUserViewModel.makeHead(authUserModel.accessToken, authUserModel.id, idUser)
        }

        binding.rlremoveBtn.setOnClickListener {
            groupOfUserViewModel.removeUser(authUserModel.accessToken, authUserModel.id, idUser)
        }
    }
}