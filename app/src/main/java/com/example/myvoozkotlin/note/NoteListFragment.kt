package com.example.myvoozkotlin.note

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentNoteListBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.main.presentation.MainFragment
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.adapters.NoteAdapter
import com.example.myvoozkotlin.note.viewModels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment: Fragment(), (Note) -> Unit {

    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }

    private val noteViewModel: NoteViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initObservers()
        initToolbar()
        checkState()
        setPaddingTopMenu()

        binding.rvNotes.hide()
        binding.llEmpty.llEmpty.show()
    }

    private fun setPaddingTopMenu() {
        binding.toolbar.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun observeOnAuthUserChangeResponse() {
        userViewModel.authUserChange.observe(viewLifecycleOwner, {
            checkState()
        })
    }

    private fun checkState(){
        binding.toolbar.menu.clear()
        when(Utils.getAuthorisationState(getCurrentUser())) {
            AuthorizationState.UNAUTORIZATE -> {
                binding.apply {
                    llNeedAutorization.root.show()
                }
            }
            AuthorizationState.AUTORIZATE -> {
                binding.apply {
                    val authUserModel = userViewModel.getCurrentAuthUser()
                    noteViewModel.loadUserNote(
                        authUserModel!!.accessToken, authUserModel.id, 0
                    )
                    llNeedAutorization.root.hide()
                }
                initToolbar()
            }
            AuthorizationState.GROUP_AUTORIZATE -> {
                binding.apply {
                    val authUserModel = userViewModel.getCurrentAuthUser()
                    noteViewModel.loadUserNote(
                        authUserModel!!.accessToken, authUserModel.id, 0
                    )
                    llNeedAutorization.root.hide()
                }
                initToolbar()
            }
        }
    }

    private fun setListeners(){
        requireActivity().supportFragmentManager.setFragmentResultListener(AddNoteFragment.REQUEST_NOTE, this) { key, bundle ->
            val note = bundle.getSerializable(AddNoteFragment.CONSTANT_NOTE) as Note
            (binding.rvNotes.adapter as? NoteAdapter)?.addNote(note)
            binding.rvNotes.show()
            (binding.root.findViewById(R.id.ll_empty) as View).hide()
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(NoteFragment.REQUEST_COMPLETED_NOTE, this) { key, bundle ->
            val idNote = bundle.getInt(NoteFragment.ID_NOTE, 0)
            (binding.rvNotes.adapter as? NoteAdapter)?.removeNote(idNote)

            if((binding.rvNotes.adapter as? NoteAdapter)!!.itemCount != 0){
                binding.rvNotes.show()
                binding.llEmpty.llEmpty.hide()
            }
            else{
                binding.rvNotes.hide()
                binding.llEmpty.llEmpty.show()
            }
        }
    }

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun configureViews() {

    }

    private fun initObservers() {
        observeOnScheduleDayResponse()
        observeOnAuthUserChangeResponse()
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
        navigator().showAddNoteScreen()
    }

    private fun initNotesAdapter(news: List<Note>) {
        if (binding.rvNotes.adapter == null) {
            binding.rvNotes.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvNotes.adapter = NoteAdapter(this)
            (binding.rvNotes.adapter as? NoteAdapter)?.update(news.toMutableList())
        } else {
            (binding.rvNotes.adapter as? NoteAdapter)?.update(news.toMutableList())
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = resources.getString(R.string.title_note)
        addBackButton()
        binding.toolbar.inflateMenu(R.menu.menu_add)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemAddNote -> {
                    loadAddNoteFragment()
                }
            }
            true
        }
    }

    private fun addBackButton(){
        val icon = resources.getDrawable(R.drawable.ic_arrow_left)
        icon.setTint(ContextCompat.getColor(requireContext(), R.color.textTertiary))
        binding.toolbar.navigationIcon = icon
        binding.toolbar.setNavigationOnClickListener {
            (parentFragment as MainFragment).openHomeList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun invoke(note: Note) {
        navigator().showNoteScreen(note)
    }
}