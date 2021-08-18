package com.example.myvoozkotlin.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentNoteBinding
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.viewModels.UserViewModel
import com.example.myvoozkotlin.models.Note
import com.example.myvoozkotlin.note.adapters.NoteAdapter
import com.example.myvoozkotlin.note.viewModels.NoteViewModel
import com.example.myvoozkotlin.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment: Fragment() {

    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }

    private val noteViewModel: NoteViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initObservers()
        initToolbar()
        checkState()
    }

    private fun checkState(){
        if (AuthorizationState.UNAUTORIZATE.ordinal == BaseApp.getAuthState()) {
            binding.apply {
                llNeedAutorization.root.show()
            }
        }
        else if (AuthorizationState.AUTORIZATE.ordinal == BaseApp.getAuthState()){
            binding.apply {
                binding.toolbar.inflateMenu(R.menu.menu_add)
                binding.toolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.itemAddNote -> {
                            loadAddNoteFragment()
                        }
                    }
                    true
                }
                val authUserModel = userViewModel.getCurrentAuthUser()
                noteViewModel.loadUserNote(
                    authUserModel.accessToken, authUserModel.id, 0
                )
                llNeedAutorization.root.hide()
            }
        }
        else if (AuthorizationState.GROUP_AUTORIZATE.ordinal == BaseApp.getAuthState()){
            binding.apply {
                binding.toolbar.inflateMenu(R.menu.menu_add)
                binding.toolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.itemAddNote -> {
                            loadAddNoteFragment()
                        }
                    }
                    true
                }
                val authUserModel = userViewModel.getCurrentAuthUser()
                noteViewModel.loadUserNote(
                    authUserModel.accessToken, authUserModel.id, 0
                )
                llNeedAutorization.root.hide()
            }
        }
    }

    private fun setListeners(){
        parentFragmentManager.setFragmentResultListener(AddNoteFragment.REQUEST_NOTE, this) { key, bundle ->
            val note = bundle.getSerializable(AddNoteFragment.CONSTANT_NOTE) as Note
            (binding.rvNotes.adapter as? NoteAdapter)?.addNote(note)
        }
    }

    private fun configureViews() {

    }

    private fun initObservers() {
        observeOnScheduleDayResponse()
    }

    private fun observeOnScheduleDayResponse() {
        noteViewModel.noteListResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.show()
                    binding.rvNotes.hide()
                    (binding.root.findViewById(R.id.ll_empty) as View).hide()
                }
                Status.SUCCESS -> {
                    binding.progressBar.hide()

                    if (it.data == null) {

                    } else {
                        initNotesAdapter(it.data)
                        if (it.data.isEmpty()) {
                            binding.rvNotes.hide()
                            (binding.root.findViewById(R.id.ll_empty) as View).show()
                        } else {
                            binding.rvNotes.show()
                            (binding.root.findViewById(R.id.ll_empty) as View).hide()
                        }
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hide()
                }
            }
        })
    }

    private fun loadAddNoteFragment(){
        val fragment = AddNoteFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.rootNoteView, fragment, AddNoteFragment.javaClass.simpleName)
            .addToBackStack("addNote")
            .commit()
    }

    private fun initNotesAdapter(news: List<Note>) {
        if (binding.rvNotes.adapter == null) {
            binding.rvNotes.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvNotes.adapter = NoteAdapter()
            (binding.rvNotes.adapter as? NoteAdapter)?.update(news.toMutableList())
        } else {
            (binding.rvNotes.adapter as? NoteAdapter)?.update(news.toMutableList())
        }
    }

//    private fun initTabLayout() {
//        val items = mutableListOf<TabItem>()
//        items.add(TabItem("Активные", 0, true))
//        items.add(TabItem("Выполненные", 0, false))
//
//        TabLayoutMediator(
//            binding.tabLayout, binding.viewPager as ViewPager2
//        ) { tab, position ->
//            run {
//                tab.text = items.get(position).name
//
//                val textView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tabitem, null)
//                (textView as TextView).text = items.get(position).name
//                tab.setId(position)
//                if(items[position].isActive){
//                    textView.setTextColor(resources.getColor(R.color.textLink))
//                }
//                tab.customView = textView
//            }
//        }.attach()
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                (tab!!.customView as TextView).setTextColor(resources.getColor(R.color.textLink))
//                if(tab.id == 0){
//
//                }
//                else{
//
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                (tab!!.customView as TextView).setTextColor(resources.getColor(R.color.textPrimary))
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })
//    }

    private fun initToolbar() {
        binding.toolbar.title = resources.getString(R.string.title_note)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}