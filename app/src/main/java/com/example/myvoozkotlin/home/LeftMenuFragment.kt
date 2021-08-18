package com.example.myvoozkotlin.home

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.MainFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.AuthFragment
import com.example.myvoozkotlin.databinding.FragmentLeftMenuBinding
import com.example.myvoozkotlin.groupOfUser.CreateGroupOfUserFragment
import com.example.myvoozkotlin.groupOfUser.GroupOfUserFragment
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.contract.navigator
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.selectGroup.SelectGroupFragment
import com.example.myvoozkotlin.user.UserFragment
import com.vk.api.sdk.VK
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeftMenuFragment : BaseFragment(), SharedPreferences.OnSharedPreferenceChangeListener,
    OnAuthUserChange {

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
        BaseApp.getSharedPref().registerOnSharedPreferenceChangeListener(this)
        configureViews()
        setListeners()
        initAuthUser()
    }

    private fun configureViews(){
        binding.navigationViewContainer.setPadding(0, BaseFragment.getStatusBarHeight(resources), 0, 0)
    }

    private fun setListeners(){
        userViewModel.changeAuthUserListener(this)

        binding.llVkSocialButton.setOnClickListener {
            openLink(Constants.APP_PREFERENCES_VK_SOCIAL_LINK)
        }

        binding.llAutorizationButton.setOnClickListener {
            loadAuthFragment()
        }

        binding.llAboutButton.setOnClickListener {
            loadAboutFragment()
        }

        binding.cvCloseButton.setOnClickListener {
            (parentFragment as? MainFragment)?.openHomeList()
        }
    }

    private fun loadAuthFragment(){
        val intent = Intent(requireActivity(), AuthFragment::class.java)
        startActivity(intent)
    }

    private fun loadAboutFragment(){
        navigator().showAboutScreen(R.id.rootMainView, parentFragmentManager)
    }

    private fun loadSelectGroupFragment(){
        val fragment = SelectGroupFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, SelectGroupFragment.javaClass.simpleName).commit()
    }

    private fun loadCreateGroupOfUserFragment(){
        val fragment = CreateGroupOfUserFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, CreateGroupOfUserFragment.javaClass.simpleName).commit()
    }

    private fun loadUserFragment(){
        val fragment = UserFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, UserFragment.javaClass.simpleName).commit()
    }

    private fun loadGroupOfUserFragment(){
        val fragment = GroupOfUserFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, GroupOfUserFragment.javaClass.simpleName).commit()
    }

    private fun initAuthUser(){
        if (AuthorizationState.UNAUTORIZATE.ordinal == BaseApp.getAuthState()) {
            binding.apply {
                llNotificationSettingButton.hide()
                llProfileSettingButton.hide()
                llLogoutButton.hide()
                llAutorizationButton.show()
                llSelectGroup.apply {
                    show()
                    setOnClickListener {
                        loadSelectGroupFragment() }
                }
                ivPhotoUser.hide()
                tvUserName.text = requireContext().getString(R.string.default_user_name)
                clPhotoContainer.setOnClickListener {}

                cvGOUButton.hide()
                cvCreateGOUButton.hide()
                cvJoinGOUButton.hide()
            }
        }
        else if (AuthorizationState.AUTORIZATE.ordinal == BaseApp.getAuthState()){
            val authUserModel = userViewModel.getCurrentAuthUser()
            binding.apply {
                llNotificationSettingButton.show()
                llProfileSettingButton.show()
                llProfileSettingButton.apply {
                    show()
                    setOnClickListener {
                        loadUserFragment()
                    }
                }
                cvCreateGOUButton.show()
                cvJoinGOUButton.show()
                cvCreateGOUButton.setOnClickListener {
                    loadCreateGroupOfUserFragment()
                }

                llAutorizationButton.hide()
                llSelectGroup.apply {
                    show()
                    setOnClickListener {
                        loadSelectGroupFragment() }
                }

                llLogoutButton.hide()
                llLogoutButton.apply {
                    show()
                    setOnClickListener {
                        VK.logout()
                        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.UNAUTORIZATE.ordinal).apply()
                    }
                }
                if(authUserModel.photo.isNotEmpty()){
                    ivPhotoUser.show()
                    Glide.with(requireContext())
                        .load(authUserModel.photo)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade(HomeFragment.ANIMATE_TRANSITION_DURATION))
                        .into(ivPhotoUser)
                }
                else
                    ivPhotoUser.hide()
                tvUserName.text = authUserModel.lastName + " " + authUserModel.firstName[0] + "."

                clPhotoContainer.setOnClickListener {
                    loadUserFragment()
                }

                cvGOUButton.hide()
            }
        }
        else if(AuthorizationState.GROUP_AUTORIZATE.ordinal == BaseApp.getAuthState()){
            val authUserModel = userViewModel.getCurrentAuthUser()
            binding.apply {

                llAutorizationButton.hide()
                llLogoutButton.hide()
                llLogoutButton.apply {
                    show()
                    setOnClickListener {
                        VK.logout()
                        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.UNAUTORIZATE.ordinal).apply()
                    }
                }

                llProfileSettingButton.show()
                llProfileSettingButton.apply {
                    show()
                    setOnClickListener {
                        loadUserFragment()
                    }
                }

                if(authUserModel.photo.isNotEmpty()){
                    ivPhotoUser.show()
                    Glide.with(requireContext())
                        .load(authUserModel.photo)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade(HomeFragment.ANIMATE_TRANSITION_DURATION))
                        .into(ivPhotoUser)
                }
                else
                    ivPhotoUser.hide()

                tvUserName.text = authUserModel.lastName + " " + authUserModel.firstName[0] + "."

                cvCreateGOUButton.hide()
                cvJoinGOUButton.hide()
                llSelectGroup.hide()

                cvGOUButton.show()
                cvGOUButton.setOnClickListener {
                    loadGroupOfUserFragment()
                }

                tvGOUName.text = authUserModel.groupOfUser?.name ?: "null"
                tvGOUGroup.text = authUserModel.groupOfUser?.nameGroup
                Glide.with(requireContext())
                    .load(authUserModel.groupOfUser!!.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(HomeFragment.ANIMATE_TRANSITION_DURATION))
                    .into(ivGOUPreview)
            }
        }
        binding.tvGroupName.text = BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_GROUP_NAME, "")
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(Constants.APP_PREFERENCES_USER_GROUP_ID)){
            initAuthUser()
        }
        else if(key.equals(Constants.APP_PREFERENCES_AUTH_STATE)){
            initAuthUser()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.getSharedPref().unregisterOnSharedPreferenceChangeListener(this)
    }

    fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    override fun onAuthUserChange() {
        initAuthUser()
    }
}