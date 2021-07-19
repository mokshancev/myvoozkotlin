package com.example.myvoozkotlin.search

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
import com.example.myvoozkotlin.databinding.FragmentSearchBinding
import com.example.myvoozkotlin.databinding.FragmentSelectGroupBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun initToolbar() {
        addBackButton()
        binding.toolbar.title = getString(R.string.title_search)
        binding.toolbar.inflateMenu(R.menu.menu_search)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_note -> Navigation.findNavController(binding.root)
                    .navigate(R.id.action_selectGroupFragment_to_searchFragment)
            }
            true
        }
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }
}