package com.example.myvoozkotlin.user

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentNoteBinding
import com.example.myvoozkotlin.databinding.FragmentUserBinding
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.home.helpers.OnTabItemPicked
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.models.TabItem
import com.example.myvoozkotlin.selectGroup.SelectGroupFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vk.api.sdk.VK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment: BaseFragment(){
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        const val ANIMATE_TRANSITION_DURATION: Int = 300
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
    }

    private fun configureViews() {
        val authUserModel = userViewModel.getCurrentAuthUser()
        if(authUserModel.photo.isNotEmpty()){
            binding.ivPhotoUser.show()
            Glide.with(this)
                .load(authUserModel.photo)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(ANIMATE_TRANSITION_DURATION))
                .into(binding.ivPhotoUser)
        }
        else
            binding.ivPhotoUser.hide()
        binding.tvUserName.text = authUserModel.lastName + " " + authUserModel.firstName[0] + "."
        binding.tvChangeUserName.text = authUserModel.lastName + " " + authUserModel.firstName
        initToolbar()
    }

    private fun setListeners(){
        binding.clChangeUserNamebutton.setOnClickListener {

        }
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_profile)
        addBackButton()

        binding.toolbar.inflateMenu(R.menu.menu_logout)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_logout -> {
                    VK.logout()
                    BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.UNAUTORIZATE.ordinal).apply()
                    parentFragmentManager.popBackStack()
                }
            }
            true
        }
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}