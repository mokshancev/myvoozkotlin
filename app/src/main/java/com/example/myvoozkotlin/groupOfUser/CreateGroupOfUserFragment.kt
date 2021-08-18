package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.data.db.realmModels.GroupOfUserModel
import com.example.myvoozkotlin.data.db.realmModels.UserVeryShortModel
import com.example.myvoozkotlin.databinding.FragmentCreateGroupOfUserBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupOfUserFragment : BaseFragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private lateinit var authUserModel: AuthUserModel

    companion object {
        fun newInstance(): CreateGroupOfUserFragment {
            return CreateGroupOfUserFragment()
        }
    }

    private var _binding: FragmentCreateGroupOfUserBinding? = null
    private val binding get() = _binding!!

    private var idUniversity = 0
    private var nameUniversity = ""
    private var idGroup = 0
    private var nameGroup = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupOfUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        initObservers()
        setListeners()

        initData()
    }

    private fun initData() {
        authUserModel = userViewModel.getCurrentAuthUser()

        nameUniversity = BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, "null")!!
        idUniversity = BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)

        nameGroup = BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_GROUP_NAME, "null")!!
        idGroup = BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0)

        binding.tvUniversityName.text = nameUniversity
        binding.tvGroupName.text = nameGroup
        binding.etGOUName.setText("Группа ${authUserModel.nameGroup}")
    }

    private fun setDefaultGroupValue(){
        idGroup = 0
        binding.tvGroupName.text = getString(R.string.not_selected)
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setListeners(){
        binding.clUniversityButton.setOnClickListener {
            loadSearchFragment(SearchEnum.UNIVERSITY.ordinal, 0)
        }

        binding.clGroupButton.setOnClickListener {
            if(idUniversity == 0)
                UtilsUI.makeToast(getString(R.string.select_university))
            else{
                loadSearchFragment(SearchEnum.GROUP.ordinal, idUniversity)
            }
        }

        binding.cvSaveButton.setOnClickListener {
            when {
                binding.etGOUName.text.length !in 1..64 ->
                    UtilsUI.makeToast(getString(R.string.toast_name_group))
                idUniversity == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_university))
                idGroup == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_group))
                else ->{
                    println("---print" + authUserModel.accessToken + " " + authUserModel.id + " " + binding.etGOUName.text.toString() + " " + idGroup)
                    groupOfUserViewModel.createGroupOfUser(authUserModel.accessToken, authUserModel.id, binding.etGOUName.text.toString(), idGroup)
                }
            }
        }

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_UNIVERSITY, this) { _, bundle ->
            val universityName = bundle.getString("fullName", "null")
            binding.tvUniversityName.text = universityName
            idUniversity = bundle.getInt("id")
            nameUniversity = universityName

            setDefaultGroupValue()
        }

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_GROUP, this) { key, bundle ->
            val groupName = bundle.getString("fullName", "null")
            binding.tvGroupName.text = groupName
            idGroup = bundle.getInt("id")
            nameGroup = groupName
        }
    }

    private fun initObservers() {
        observeOnCreateGroupOfUserResponse()
    }

    private fun observeOnCreateGroupOfUserResponse() {
        groupOfUserViewModel.createGroupOfUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    setLoadStateBtn()
                }
                Status.SUCCESS -> {
                    setOpenStateBtn()

                    if (it.data == null) {

                    } else {
                        println("gou " + it.data)
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
                        userViewModel.updateGroupOfUser(groupOfUserModel)
                        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_AUTH_STATE, AuthorizationState.GROUP_AUTORIZATE.ordinal).apply()
                        requireActivity().onBackPressed()

                    }
                }
                Status.ERROR -> {
                    setOpenStateBtn()
                }
            }
        })
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

    private fun loadSearchFragment(typeSearch: Int, addParam: Int){
        val fragment = SearchFragment.newInstance(typeSearch, addParam)
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootCreateGroupOfUserView, fragment, SearchFragment.javaClass.simpleName).commit()
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_create_group_of_user)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}