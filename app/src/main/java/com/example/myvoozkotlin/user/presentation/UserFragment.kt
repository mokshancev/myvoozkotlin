package com.example.myvoozkotlin.user.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentUserBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.contract.navigator
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.user.ChangeFullNameDialogFragment
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class UserFragment: Fragment() {
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        const val ANIMATE_TRANSITION_DURATION: Int = 300
        const val IMAGE_TYPE_REQUEST: String = "image_profile"
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
        initData(userViewModel.getCurrentAuthUser())
        configureViews()
        setListeners()
        initObservers()
    }

    private fun configureViews() {
        initToolbar()
    }

    private fun initData(authUserModel: AuthUserModel?) {
        authUserModel?.apply {
            showPhotoUserImage(true, photo)
            "$lastName ${firstName[0]}".also { binding.tvUserName.text = it }
            ("$lastName $firstName").also { binding.tvChangeUserName.text = it }
        }
    }

    private fun setListeners(){
        setChangeUserNameButton()
        setPhotoContainerListener()
    }

    private fun showPhotoUserImage(state: Boolean, url: String) =
        showImage(binding.ivPhotoUser.id, state, url)

    private fun setPhotoContainerListener() {
        binding.cvPhotoContainer.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
            photoPickerIntent.type = "image/*"
            photoPickerResult.launch(photoPickerIntent)
        }
    }

    private fun setChangeUserNameButton() {
        binding.clChangeUserNamebutton.setOnClickListener {
            navigator().showDialog(ChangeFullNameDialogFragment())
        }
    }

    private var photoPickerResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val contentURI = result.data!!.data
                try {
                    true.showWait()
                    getCurrentUser()?.let {
                        val bitmap = UtilsUI.convertUriToBitmap(contentURI!!)
                        userViewModel.uploadImage(bitmap, it.accessToken, it.id, IMAGE_TYPE_REQUEST)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun Boolean.showWait() = (requireActivity() as MainActivity).showWait(this)

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_profile)
        addBackButton()

        binding.toolbar.inflateMenu(R.menu.menu_logout)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_logout -> {
                    userViewModel.removeCurrentUser()
                    navigator().goBack()
                }
            }
            true
        }
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun initObservers() {
        observeOnAuthUserChangeResponse()
    }

    private fun observeOnAuthUserChangeResponse() {
        userViewModel.authUserChange.observe(viewLifecycleOwner, {
            initData(userViewModel.getCurrentAuthUser())
        })
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
}