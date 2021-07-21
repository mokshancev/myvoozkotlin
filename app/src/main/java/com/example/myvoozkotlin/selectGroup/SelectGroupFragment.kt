package com.example.myvoozkotlin.selectGroup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum


class SelectGroupFragment : Fragment() {

    companion object {
        fun newInstance(): SelectGroupFragment {
            return SelectGroupFragment()
        }
    }

    private var _binding: FragmentSelectGroupBinding? = null
    private val binding get() = _binding!!

    private var idUniversity = 0
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

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_UNIVERSITY, this) { key, bundle ->
            val universityName = bundle.getString("fullName")
            binding.tvUniversityName.text = universityName
            idUniversity = bundle.getInt("id")
            setDefaultGroupValue()
        }

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_GROUP, this) { key, bundle ->
            val groupName = bundle.getString("fullName")
            binding.tvGroupName.text = groupName
            idGroup = bundle.getInt("id")
            nameGroup = bundle.getString("fullName", "")
        }
    }

    private fun setDefaultGroupValue(){
        idGroup = 0
        binding.tvGroupName.text = "Не выбрано"
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setListeners(){
        binding.clUniversityButton.setOnClickListener {
            val fragment = SearchFragment.newInstance(SearchEnum.UNIVERSITY.ordinal, 0)
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootViewSelectGroup, fragment, SearchFragment.javaClass.simpleName).commit()
        }

        binding.clGroupButton.setOnClickListener {
            val fragment = SearchFragment.newInstance(SearchEnum.GROUP.ordinal, idUniversity)
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootViewSelectGroup, fragment, SearchFragment.javaClass.simpleName).commit()
        }

        binding.cvSaveButton.setOnClickListener {
            if(idGroup == 0){
                UtilsUI.MakeToast(getString(R.string.toast_select_group))
            }
            else{
                BaseApp.getSharedPref().edit().putString(Constants.APP_PREFERENCES_USER_GROUP_NAME, nameGroup)
                BaseApp.getSharedPref().edit().putInt(Constants.APP_PREFERENCES_USER_GROUP_ID, idGroup)
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_select_group)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireParentFragment().childFragmentManager.popBackStack()
        }
    }
}