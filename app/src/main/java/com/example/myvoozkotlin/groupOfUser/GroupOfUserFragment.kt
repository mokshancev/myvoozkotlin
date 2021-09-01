package com.example.myvoozkotlin.groupOfUser

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentGroupOfUserBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.user.presentation.UserFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class GroupOfUserFragment: Fragment(){
    companion object {
        const val ANIMATE_TRANSITION_DURATION: Int = 300
        fun newInstance(): GroupOfUserFragment {
            return GroupOfUserFragment()
        }
    }

    private var _binding: FragmentGroupOfUserBinding? = null
    private val binding get() = _binding!!
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var authUserModel:AuthUserModel? = null

    private var nameGroup = ""
    private var idGroup = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupOfUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authUserModel = userViewModel.getCurrentAuthUser()
        configureViews()
        initData()
        initObservers()
        setListeners()
        groupOfUserViewModel.getGroupOfUserUser(authUserModel!!.accessToken, authUserModel!!.id)
    }

    private fun configureViews() {
        initToolbar()
        if(authUserModel!!.photo.isNotEmpty()){
            binding.ivPhotoUser.show()

        }
        else
            binding.ivPhotoUser.hide()
        binding.tvUserName.text = authUserModel!!.groupOfUser!!.name
        setPaddingTopMenu()
    }

    private fun setPaddingTopMenu() {
        binding.toolbar.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun setListeners(){

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_GROUP, this) { key, bundle ->
            nameGroup = bundle.getString("fullName", "null")
            idGroup = bundle.getInt("id", 0)
            groupOfUserViewModel.changeIdGroup(authUserModel!!.accessToken, authUserModel!!.id, idGroup)
            (requireActivity() as MainActivity).showWait(true)
        }

        binding.clChangeGroupButton.setOnClickListener {
            loadSearchFragment(SearchEnum.GROUP.ordinal, authUserModel!!.groupOfUser!!.idUniversity)
        }

        binding.cvChangeNameGOUButton.setOnClickListener {
            val fragment = ChangeNameDialogFragment()
            fragment.show(parentFragmentManager,
                ChangeNameDialogFragment::javaClass.javaClass.simpleName)
        }

        binding.cvAddUser.setOnClickListener {
            val fragment = InviteGouDialogFragment()
            fragment.show(parentFragmentManager,
                InviteGouDialogFragment::javaClass.javaClass.simpleName)
        }

        binding.clUserListGroupButton.setOnClickListener {
            loadUserListFragment()
        }

        binding.cvPhotoContainer.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }

        setNotificationClickListener()
    }

    private fun setNotificationClickListener(){
//        binding.llNotification.setOnClickListener {
//            openNotificationFragment()
//        }
    }

    private fun initData(){
        binding.tvChangeGroupOfUserName.text = authUserModel!!.groupOfUser!!.name
        binding.tvChangeGroup.text = authUserModel!!.groupOfUser!!.nameGroup
        binding.tvUniversityName.text = authUserModel!!.groupOfUser!!.nameUniversity
        binding.tvGroupName.text = authUserModel!!.groupOfUser!!.nameGroup
        binding.tvCountUsers.text = authUserModel!!.groupOfUser!!.countUsers.toString()
        binding.tvHeadUser.text = authUserModel!!.groupOfUser!!.userVeryShortModel!!.name
        binding.tvChangeGroupOfUserName.text = authUserModel!!.groupOfUser!!.name
        binding.tvPostTitleListGroup.text = "${authUserModel!!.groupOfUser!!.countUsers} чел."
        binding.tvChangeGroup.text = authUserModel!!.groupOfUser!!.nameGroup
        Glide.with(this)
            .load(authUserModel!!.groupOfUser!!.image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(UserFragment.ANIMATE_TRANSITION_DURATION))
            .into(binding.ivPhotoUser)
    }

    private fun initObservers() {
        observeOnChangeIdGroupResponse()
        observeOnLogoutGroupOfUserResponse()
        observeOnAuthUserChangeResponse()
        observeOnGetGroupOfUserResponse()
    }

    private fun observeOnChangeIdGroupResponse() {
        groupOfUserViewModel.changeGroupResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        (requireActivity() as MainActivity).showWait(false)
                        groupOfUserViewModel.changeGroupGOU(idGroup, nameGroup)
                        binding.tvGroupName.text = nameGroup
                        binding.tvChangeGroup.text = nameGroup
                        UtilsUI.makeToast(getString(R.string.toast_group_of_user_change_group))
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun observeOnGetGroupOfUserResponse() {
        groupOfUserViewModel.groupOfUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {

                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, contentURI)
                    (requireActivity() as MainActivity).showWait(true)
                    userViewModel.uploadImage(bitmap, authUserModel!!.accessToken, authUserModel!!.id, "group_profile")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun observeOnLogoutGroupOfUserResponse() {
        groupOfUserViewModel.logoutGroupOfUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        val authUserModel = userViewModel.getCurrentAuthUser()
                        authUserModel!!.idGroupOfUser = 0
                        userViewModel.setCurrentUser(authUserModel)
                        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.AUTORIZATE.ordinal).apply()
                        (requireActivity() as MainActivity).showWait(false)
                        requireActivity().onBackPressed()
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun observeOnAuthUserChangeResponse() {
        userViewModel.authUserChange.observe(viewLifecycleOwner, {
            authUserModel = userViewModel.getCurrentAuthUser()
            (requireActivity() as MainActivity).showWait(false)
            if(authUserModel!!.idGroupOfUser == 0){
                requireActivity().onBackPressed()
            }
            else{
                initData()
            }
        })
    }

    private fun loadSearchFragment(typeSearch: Int, addParam: Int){
        val fragment = SearchFragment.newInstance(typeSearch, addParam)
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.rootGroupOfUserView, fragment, SearchFragment.javaClass.simpleName).commit()
    }

    private fun loadUserListFragment(){
        val fragment = UserListGroupOfUserFragment.newInstance()
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.rootGroupOfUserView, fragment, UserListGroupOfUserFragment.javaClass.simpleName).commit()
    }

    private fun openNotificationFragment(){
        navigator().showCreateNotificationGOUScreen()
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_group)
        addBackButton()

        binding.toolbar.inflateMenu(R.menu.menu_logout)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_logout -> {
                    (requireActivity() as MainActivity).showWait(true)
                    groupOfUserViewModel.logoutGroupOfUser(authUserModel!!.accessToken, authUserModel!!.id)
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