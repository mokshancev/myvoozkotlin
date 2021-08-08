package com.example.myvoozkotlin.selectGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum


class SelectGroupFragment : BaseFragment() {

    companion object {
        fun newInstance(): SelectGroupFragment {
            return SelectGroupFragment()
        }
    }

    private var _binding: FragmentSelectGroupBinding? = null
    private val binding get() = _binding!!

    private var idUniversity = 0
    private var nameUniversity = ""
    private var idGroup = 0
    private var nameGroup = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()

        initData()
    }

    private fun initData() {
        nameUniversity = BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_UNIVERSITY_NAME, "null")!!
        idUniversity = BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)

        nameGroup = BaseApp.getSharedPref().getString(Constants.APP_PREFERENCES_USER_GROUP_NAME, "null")!!
        idGroup = BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0)

        binding.tvUniversityName.text = nameUniversity
        binding.tvGroupName.text = nameGroup
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
                idUniversity == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_university))
                idGroup == 0 ->
                    UtilsUI.makeToast(getString(R.string.toast_select_group))
                else -> {
                    saveSelectValue()
                    parentFragmentManager.popBackStack()
                }
            }
        }

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_UNIVERSITY, this) { key, bundle ->
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
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}