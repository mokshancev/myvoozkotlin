package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.data.db.realmModels.UserVeryShortModel
import com.example.myvoozkotlin.databinding.FragmentCreateGroupOfUserBinding
import com.example.myvoozkotlin.databinding.FragmentGroupOfUserBinding
import com.example.myvoozkotlin.databinding.FragmentUserBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.home.helpers.OnAuthUserChange
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.user.ChangeFullNameDialogFragment
import com.example.myvoozkotlin.user.UserFragment
import com.vk.api.sdk.VK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupOfUserFragment: BaseFragment(), OnAuthUserChange {
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
    }

    private fun configureViews() {
        initToolbar()
        if(authUserModel!!.photo.isNotEmpty()){
            binding.ivPhotoUser.show()
            Glide.with(this)
                .load(authUserModel!!.groupOfUser!!.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(UserFragment.ANIMATE_TRANSITION_DURATION))
                .into(binding.ivPhotoUser)
        }
        else
            binding.ivPhotoUser.hide()
        binding.tvUserName.text = authUserModel!!.groupOfUser!!.name
    }

    private fun setListeners(){
        userViewModel.changeAuthUserListener(this)

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

        binding.llInvite.setOnClickListener {
            val fragment = InviteGouDialogFragment()
            fragment.show(parentFragmentManager,
                InviteGouDialogFragment::javaClass.javaClass.simpleName)
        }

        binding.llUsers.setOnClickListener {
            loadUserListFragment()
        }
    }

    private fun initData(){
        binding.tvChangeGroupOfUserName.text = authUserModel!!.groupOfUser!!.name
        binding.tvChangeGroup.text = authUserModel!!.groupOfUser!!.nameGroup
        binding.tvUniversityName.text = authUserModel!!.groupOfUser!!.nameUniversity
        binding.tvGroupName.text = authUserModel!!.groupOfUser!!.nameGroup
        binding.tvCountUsers.text = authUserModel!!.groupOfUser!!.countUsers.toString()
        binding.tvHeadUser.text = authUserModel!!.groupOfUser!!.userVeryShortModel!!.name
        binding.tvChangeGroupOfUserName.text = authUserModel!!.groupOfUser!!.name
        binding.tvChangeGroup.text = authUserModel!!.groupOfUser!!.nameGroup
    }

    private fun initObservers() {
        observeOnChangeIdGroupResponse()
        observeOnLogoutGroupOfUserResponse()
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

    private fun observeOnLogoutGroupOfUserResponse() {
        groupOfUserViewModel.logoutGroupOfUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
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

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.changeAuthUserListener(null)
    }

    override fun onAuthUserChange() {
        authUserModel = userViewModel.getCurrentAuthUser()
        initData()
    }
}