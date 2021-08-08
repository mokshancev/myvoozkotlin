package com.example.myvoozkotlin.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentAboutBinding
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.contract.navigator
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum


class AboutFragment : BaseFragment() {

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        requireFragmentManager().fragments.forEachIndexed { _, fragment ->
            println("tagggg " + fragment.tag)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setListeners(){
        binding.clFeedbackButton.setOnClickListener {
            openLink(Constants.APP_PREFERENCES_VK_FEEDBACK)
        }

        binding.clPrivacyPolicyButton.setOnClickListener {
            openLink(Constants.APP_PREFERENCES_VK_PRIVACY_POLICY)
        }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_about)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}