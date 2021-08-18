package com.example.myvoozkotlin.user

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentUserBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.vk.api.sdk.VK
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class UserFragment: BaseFragment(), OnAuthUserChange {
    private val userViewModel: UserViewModel by viewModels()
    private var authUserModel: AuthUserModel? = null

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
        authUserModel = userViewModel.getCurrentAuthUser()
        initData()
        initToolbar()
    }

    private fun initData(){
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, contentURI)

                    userViewModel.uploadImage(bitmap, authUserModel!!.accessToken, authUserModel!!.id)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setListeners(){
        userViewModel.changeAuthUserListener(this)

        binding.clChangeUserNamebutton.setOnClickListener {
            val fragment = ChangeFullNameDialogFragment()
            fragment.show(parentFragmentManager,
                ChangeFullNameDialogFragment::javaClass.javaClass.simpleName)
        }

        binding.cvPhotoContainer.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
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
            requireActivity().onBackPressed()
        }
    }

    override fun onAuthUserChange() {
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.changeAuthUserListener(null)
    }
}