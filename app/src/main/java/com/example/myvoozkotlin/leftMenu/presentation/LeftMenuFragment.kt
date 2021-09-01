package com.example.myvoozkotlin.leftMenu.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.*
import com.example.myvoozkotlin.auth.AuthActivity
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentLeftMenuBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.main.presentation.MainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeftMenuFragment : Fragment() {

    companion object {
        fun newInstance(): LeftMenuFragment {
            return LeftMenuFragment()
        }
    }

    private var _binding: FragmentLeftMenuBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeftMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initObservers()
        initBlocks(userViewModel.getCurrentAuthUser())
    }

    private fun configureViews() {
        setPaddingTopMenu()
    }

    private fun setListeners() {
        setVkClickListener()
        setAboutClickListener()
        setLeftMenuClickListener()
        setNotificationClickListener()
    }

    private fun setPaddingTopMenu() {
        binding.navigationViewContainer.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun setLeftMenuClickListener() {
        binding.cvCloseButton.setOnClickListener {
            (parentFragment as? MainFragment)?.openHomeList()
        }
    }

    private fun setVkClickListener() {
        binding.llVkSocialButton.setOnClickListener {
            openLink(Constants.APP_PREFERENCES_VK_SOCIAL_LINK)
        }
    }

    private fun setNotificationClickListener() {
        binding.cvNotificationButton.setOnClickListener {
            navigator().showNotificationScreen()
        }
    }

    private fun initObservers() {
        observeOnAuthUserChangeResponse()
    }

    private fun observeOnAuthUserChangeResponse() {
        userViewModel.authUserChange.observe(viewLifecycleOwner, {
            initBlocks(userViewModel.getCurrentAuthUser())
        })
    }

    private fun setAboutClickListener() {
        binding.llAboutButton.setOnClickListener {
            navigator().showAboutScreen()
        }
    }

    private fun openAuthActivity() {
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
    }

    private fun openSelectGroupFragment() {
        navigator().showSelectGroupScreen(R.id.rootMainView,
            isFirst = false,
            isBackStack = true
        )
    }

    private fun openCreateGroupOfUserFragment() {
        navigator().showCreateGroupOfUserScreen()
    }

    private fun openUserFragment() {
        navigator().showUserScreen()
    }

    private fun openGroupOfUserFragment() {
        navigator().showGroupOfUserScreen()
    }

    private fun openNotificationFragment() {
        navigator().showNotificationScreen()
    }

    private fun openInviteGroupOfUserFragment() {
        navigator().showInviteGroupOfUserScreen()
    }

    private fun initGroupOfUserBlock(authUserModel: AuthUserModel?) {
        binding.apply {
            when (Utils.getAuthorisationState(authUserModel)) {
                AuthorizationState.UNAUTORIZATE -> {
                    showCreateGOUButton(false)
                    showInviteGOUButton(false)
                    showGOUButton(false)
                }
                AuthorizationState.AUTORIZATE -> {
                    showCreateGOUButton(true)
                    showInviteGOUButton(true)
                    showGOUButton(false)
                }
                AuthorizationState.GROUP_AUTORIZATE -> {
                    showCreateGOUButton(false)
                    showInviteGOUButton(false)
                    showGOUButton(true)

                    tvGOUName.text = authUserModel!!.groupOfUser!!.name
                    tvGOUGroup.text = authUserModel.groupOfUser!!.nameGroup

                    showPhotoGOUImage(true, authUserModel.groupOfUser!!.image)
                }
            }
        }
    }

    private fun initUserBlock(authUserModel: AuthUserModel?) {
        when (Utils.getAuthorisationState(authUserModel)) {
            AuthorizationState.UNAUTORIZATE -> {
                showPhotoUserImage(false, "")

                showUserButton(false)
                showLogoutButton(false)
                showNotificationSettingButton(false)
                showLoginButton(true)
                showSelectGroupButton(true)

                setNameUser(requireContext().getString(R.string.default_user_name))
                setNameGroup(userViewModel.getNameGroup())
            }
            AuthorizationState.AUTORIZATE -> {
                showPhotoUserImage(true, authUserModel!!.photo)

                showUserButton(true)
                showLogoutButton(true)
                showNotificationSettingButton(false)
                showLoginButton(false)
                showSelectGroupButton(true)

                setNameUser(authUserModel.lastName + " " + authUserModel.firstName[0] + ".")
                setNameGroup(authUserModel.nameGroup)
            }
            AuthorizationState.GROUP_AUTORIZATE -> {
                showPhotoUserImage(true, authUserModel!!.photo)

                showUserButton(true)
                showLogoutButton(true)
                showNotificationSettingButton(false)
                showLoginButton(false)
                showSelectGroupButton(false)

                setNameUser("${authUserModel.lastName} ${authUserModel.firstName[0]}.")
                setNameGroup(authUserModel.groupOfUser!!.nameGroup)
            }
        }
    }

    private fun showUserButton(state: Boolean) =
        showButton(binding.llProfileSettingButton.id, state) { openUserFragment() }

    private fun showLogoutButton(state: Boolean) =
        showButton(binding.llLogoutButton.id, state) {
            val fragment = ConfirmLogoutDialogFragment()
            fragment.show(parentFragmentManager,
                ConfirmLogoutDialogFragment::javaClass.javaClass.simpleName) }

    private fun showNotificationSettingButton(state: Boolean) =
        showButton(binding.llNotificationSettingButton.id, state) { openNotificationFragment() }

    private fun showLoginButton(state: Boolean) =
        showButton(binding.llAutorizationButton.id, state) { openAuthActivity() }

    private fun showCreateGOUButton(state: Boolean) =
        showButton(binding.cvCreateGOUButton.id, state) { openCreateGroupOfUserFragment() }

    private fun showInviteGOUButton(state: Boolean) =
        showButton(binding.cvInviteGOUButton.id, state) { openInviteGroupOfUserFragment() }

    private fun showGOUButton(state: Boolean) =
        showButton(binding.cvGOUButton.id, state) { openGroupOfUserFragment() }

    private fun showSelectGroupButton(state: Boolean) =
        showButton(binding.llSelectGroup.id, state) { openSelectGroupFragment() }

    private fun showPhotoUserImage(state: Boolean, url: String) =
        showImage(binding.ivPhotoUser.id, state, url)

    private fun showPhotoGOUImage(state: Boolean, url: String) =
        showImage(binding.ivGOUPreview.id, state, url)

    private fun setNameGroup(nameGroup: String) {
        binding.tvGroupName.text = nameGroup
    }

    private fun setNameUser(nameUser: String) {
        binding.tvUserName.text = nameUser
    }

    private fun showImage(
        @IdRes idRes: Int,
        state: Boolean,
        url: String?
    ) {
        binding.root.findViewById<View>(idRes).apply {
            if (state) {
                show()
                Glide.with(requireContext())
                    .load(url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(HomeFragment.ANIMATE_TRANSITION_DURATION))
                    .into(this as ImageView)
            } else
                hide()
        }
    }

    private fun showButton(
        @IdRes idRes: Int,
        state: Boolean,
        itemClickListener: () -> Unit
    ) {
        binding.root.findViewById<View>(idRes).apply {
            if (state) {
                show()
                setOnClickListener {
                    itemClickListener.invoke()
                }
            } else
                hide()
        }
    }

    private fun initBlocks(authUserModel: AuthUserModel?) {
        initGroupOfUserBlock(authUserModel)
        initUserBlock(authUserModel)
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}