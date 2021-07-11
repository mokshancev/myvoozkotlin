package com.example.myvoozkotlin.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentNoteBinding
import com.example.myvoozkotlin.home.adapters.WeekAdapter
import com.example.myvoozkotlin.home.helpers.OnTabItemPicked
import com.example.myvoozkotlin.models.TabItem
import com.example.myvoozkotlin.note.adapters.TabNoteAdapter
import java.util.*

class NoteFragment: Fragment(), OnTabItemPicked {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initTabItemAdapter()
    }

    private fun initTabItemAdapter() {
        val items = mutableListOf<TabItem>()
        items.add(TabItem("Активные", 0, true))
        items.add(TabItem("Выполненные", 0, false))
        if (binding.rvItem.adapter == null) {
            binding.rvItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvItem.adapter = TabNoteAdapter(items, this)
        } else {
            (binding.rvItem.adapter as? TabNoteAdapter)?.update(items)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = resources.getString(R.string.title_note)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    override fun onTabItemClick(position: Int) {

    }
}