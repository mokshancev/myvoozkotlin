package com.example.myvoozkotlin.selectGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentHomeBinding
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding


class SelectGroupFragment : Fragment() {
    private var _binding: FragmentSelectGroupBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setListeners(){
        binding.clUniversityButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_selectGroupFragment_to_searchFragment)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_select_group)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }
}