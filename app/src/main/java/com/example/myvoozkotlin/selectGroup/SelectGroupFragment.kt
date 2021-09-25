package com.example.myvoozkotlin.selectGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectGroupFragment : Fragment() {

    companion object {
        const val FIRST_OPEN = "first_open"
        fun newInstance(isFirst: Boolean): SelectGroupFragment {
            val fragment = SelectGroupFragment()
            fragment.arguments = bundleOf(FIRST_OPEN to isFirst)
            return fragment
        }
    }

    private var _binding: FragmentSelectGroupBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    private var idUniversity = 0
    private var nameUniversity = ""
    private var idGroup = 0
    private var nameGroup = ""
    private var isFirst = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIntentData()
        configureViews()
        setListeners()
        initObservers()
        initData()
        setPaddingTopMenu()
    }

    private fun setPaddingTopMenu() {
        binding.toolbar.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun initIntentData(){
        if(requireArguments() != null){
            isFirst = requireArguments().getBoolean(FIRST_OPEN, false)
        }
    }

    private fun initData() {
        nameUniversity = userViewModel.getNameUniversity()
        idUniversity = userViewModel.getIdUniversity()

        nameGroup = userViewModel.getNameGroup()
        idGroup = userViewModel.getIdGroup()

        binding.tvUniversityName.text = nameUniversity
        binding.tvGroupName.text = nameGroup
    }

    private fun setDefaultGroupValue(){
        idGroup = 0
        binding.tvGroupName.text = getString(R.string.not_selected)
    }

    private fun configureViews(){

        if(isFirst){
            binding.ivPreview.show()
            binding.tvTitle.show()
        }
        else{
            initToolbar()
        }
    }

    private fun setListeners(){
        setUniversityClickListener()
        setGroupClickListener()
        setSaveClickListener()

        setUniversityResultListener()
        setGroupResultListener()
    }

    private fun setUniversityResultListener(){
        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_UNIVERSITY, this) { key, bundle ->
            val universityName = bundle.getString(SearchFragment.KEY_FULL_NAME, "null")
            binding.tvUniversityName.text = universityName
            idUniversity = bundle.getInt(SearchFragment.KEY_ID)
            nameUniversity = universityName

            setDefaultGroupValue()
        }
    }

    private fun setGroupResultListener(){
        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_GROUP, this) { key, bundle ->
            val groupName = bundle.getString(SearchFragment.KEY_FULL_NAME, "null")
            binding.tvGroupName.text = groupName
            idGroup = bundle.getInt(SearchFragment.KEY_ID)
            nameGroup = groupName
        }
    }

    private fun setGroupClickListener(){
        binding.clGroupButton.setOnClickListener {
            if(idUniversity == 0)
                UtilsUI.makeToast(getString(R.string.select_university))
            else{
                loadSearchFragment(SearchEnum.GROUP.ordinal, idUniversity)
            }
        }
    }

    private fun setUniversityClickListener(){
        binding.clUniversityButton.setOnClickListener {
            loadSearchFragment(SearchEnum.UNIVERSITY.ordinal, 0)
        }
    }

    private fun setSaveClickListener(){
        binding.cvSaveButton.setOnClickListener {
            when {
                idUniversity == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_university))
                idGroup == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_group))
                else -> {
                    saveSelectValue()
                    if(isFirst){
                        navigator().showMainScreen()
                    }
                    else{
                        if(userViewModel.getCurrentAuthUser() == null){
                            parentFragmentManager.popBackStack()
                        }
                        else{
                            userViewModel.getCurrentAuthUser()?.let {
                                userViewModel.changeIdGroupUser(it.accessToken, it.id, nameGroup, idGroup)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initObservers() {
        observeOnChangeIdGroupResponse()
    }

    private fun saveSelectValue() {
        BaseApp.getSharedPref().edit().putString(Constants.APP_PREFERENCES_USER_GROUP_NAME, nameGroup).apply()
        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_USER_GROUP_ID, idGroup).apply()

        BaseApp.getSharedPref().edit().putString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, nameUniversity).apply()
        BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, idUniversity).apply()
    }

    private fun loadSearchFragment(typeSearch: Int, addParam: Int){
        val fragment = SearchFragment.newInstance(typeSearch, addParam)
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.rootSelectGroupView, fragment, SearchFragment.javaClass.simpleName).commit()
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_select_group)
        if(!isFirst){
            addBackButton()
        }
    }

    private fun observeOnChangeIdGroupResponse() {
        userViewModel.changeIdGroupUserResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    parentFragmentManager.popBackStack()
                }
                Status.ERROR -> {
                    (requireActivity() as MainActivity).showWait(false)
                }
            }
        })
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}