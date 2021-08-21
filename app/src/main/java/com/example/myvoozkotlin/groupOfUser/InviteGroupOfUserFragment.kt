package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.data.db.realmModels.UserVeryShortModel
import com.example.myvoozkotlin.databinding.FragmentInviteGroupOfUserBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InviteGroupOfUserFragment : BaseFragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private var authUserModel: AuthUserModel? = null

    companion object {
        fun newInstance(): InviteGroupOfUserFragment {
            return InviteGroupOfUserFragment()
        }
    }

    private var _binding: FragmentInviteGroupOfUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteGroupOfUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        initObservers()
        setListeners()
        authUserModel = userViewModel.getCurrentAuthUser()

    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setListeners(){
        binding.cvSaveButton.setOnClickListener {
            when {
                binding.etGOUName.text.trim().length != 8->
                    UtilsUI.makeToast(getString(R.string.toast_invite_gou))
                else ->{
                    groupOfUserViewModel.inviteGroupOfUser(authUserModel!!.accessToken, authUserModel!!.id, binding.etGOUName.text.toString())
                }
            }
        }
    }

    private fun initObservers() {
        observeOnCreateGroupOfUserResponse()
    }

    private fun observeOnCreateGroupOfUserResponse() {
        groupOfUserViewModel.inviteGroupOfUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    if (it.data != null) {
                        val groupOfUserModel = GroupOfUserModel()
                        groupOfUserModel.id = it.data.groupOfUser.id
                        groupOfUserModel.idCreator = it.data.groupOfUser.idCreator
                        groupOfUserModel.idOlder = it.data.groupOfUser.idOlder
                        groupOfUserModel.idGroup = it.data.groupOfUser.idGroup
                        groupOfUserModel.countUsers = it.data.groupOfUser.countUsers
                        groupOfUserModel.name = it.data.groupOfUser.name
                        groupOfUserModel.nameUniversity = it.data.groupOfUser.nameUniversity
                        groupOfUserModel.idUniversity = it.data.groupOfUser.idUniversity
                        groupOfUserModel.nameGroup = it.data.groupOfUser.nameGroup
                        groupOfUserModel.image = it.data.groupOfUser.image

                        val userVeryShortModel = UserVeryShortModel()
                        userVeryShortModel.id = it.data.groupOfUser.userVeryShort.id
                        userVeryShortModel.name = it.data.groupOfUser.userVeryShort.name
                        userVeryShortModel.photo = it.data.groupOfUser.userVeryShort.photo
                        groupOfUserModel.userVeryShortModel = userVeryShortModel
                        val authUserModel = userViewModel.getCurrentAuthUser()
                        authUserModel!!.idGroupOfUser = groupOfUserModel.id
                        authUserModel.groupOfUser = groupOfUserModel
                        userViewModel.setCurrentUser(authUserModel)
                        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.GROUP_AUTORIZATE.ordinal).apply()
                        requireActivity().onBackPressed()

                    }
                }
                Status.ERROR -> {
                    (requireActivity() as MainActivity).showWait(false)
                }
            }
        })
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_join_to_gou)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}