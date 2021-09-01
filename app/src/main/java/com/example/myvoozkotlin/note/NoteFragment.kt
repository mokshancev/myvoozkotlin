package com.example.myvoozkotlin.note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentNoteBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.note.adapters.PhotoSliderAdapter
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.note.viewModels.NoteViewModel
import com.example.myvoozkotlin.photo.PhotoFragment
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteFragment: Fragment(), (PhotoItem) -> Unit {
    private val userViewModel: UserViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    companion object {
        const val REQUEST_COMPLETED_NOTE = "note_response"
        const val ID_NOTE = "id_note"
        const val ANIMATE_TRANSITION_DURATION: Int = 300
        const val IMAGE_TYPE_REQUEST: String = "image_profile"
        const val ITEM_NOTE_KEY: String = "note"
        fun newInstance(note: Note): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundleOf(ITEM_NOTE_KEY to note)
            return fragment
        }
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var note:Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        note = requireArguments().getSerializable(ITEM_NOTE_KEY) as Note
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initObservers()
        initData()

        if(note!!.images.isNotEmpty()){
            binding.ivDefaultPreview.hide()
        }
    }

    private fun initData(){
        binding.apply {
            note?.let {
                tvTitle.text = it.name
                tvPostTitle.text = it.text
                tvObject.text = it.nameObject

                if(it.markMe){
                    ivMark.show()
                }
                else{
                    ivMark.hide()
                }

                val calNextDay = Calendar.getInstance()
                calNextDay.timeInMillis = System.currentTimeMillis()
                calNextDay.add(Calendar.DATE, 1)

                val calCur = Calendar.getInstance()
                calCur.timeInMillis = System.currentTimeMillis()

                val calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
                try {
                    calendar.time = sdf.parse(it.date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                val ii: String = Utils.getMonthName(calendar[Calendar.MONTH])
                val minutes =
                    if (calendar[Calendar.MINUTE] < 10) "0" + calendar[Calendar.MINUTE] else calendar[Calendar.MINUTE].toString()

                if (calCur.after(calendar)) {
                    if (calendar[Calendar.MONTH] < 10) "0" + calendar[Calendar.MONTH] else calendar[Calendar.MONTH].toString()
                    binding.tvDate.text = "Истек, " + calendar[Calendar.DAY_OF_MONTH] + " " + ii + " в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes

                } else {
                    if (calNextDay.after(calendar)) {
                        if (calCur[Calendar.DAY_OF_YEAR] == calendar[Calendar.DAY_OF_YEAR]) {
                            binding.tvDate.text = "Сегодня в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes
                        } else {
                            binding.tvDate.text = "Завтра в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes
                        }
                    } else {
                        binding.tvDate.text = Utils.getDayName(calendar[Calendar.DAY_OF_WEEK]) + ", " + calendar[Calendar.DAY_OF_MONTH] + " " + ii + " в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes
                    }
                }
            }
        }
    }

    private fun observeOnAddNoteResponse() {
        noteViewModel.completedNoteResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    parentFragmentManager.setFragmentResult(REQUEST_COMPLETED_NOTE, bundleOf(ID_NOTE to note!!.id))
                    parentFragmentManager.popBackStack();
                    }
                Status.ERROR -> {
                    (requireActivity() as MainActivity).showWait(false)
                }
            }
        })
    }

    private fun setPaddingTopMenu() {
        binding.llMenu.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun configureViews() {
        initTabLayout()
        setPaddingTopMenu()
    }

    private fun setListeners(){
        setCloseClickListener()
        setDeleteClickListener()
    }

    private fun setCloseClickListener(){
        binding.cvCloseButton.setOnClickListener {
            navigator().goBack()
        }
    }

    private fun setDeleteClickListener(){
        binding.cvDeleteButton.setOnClickListener {
            noteViewModel.completedNote(getCurrentUser()!!.accessToken, getCurrentUser()!!.id, listOf(
                note!!.id))
        }
    }

    private fun initTabLayout() {
        val sliderAdapter = note?.let { PhotoSliderAdapter(it.images, this) }
        binding.vpPhoto.adapter = sliderAdapter
    }

    private fun Boolean.showWait() = (requireActivity() as MainActivity).showWait(this)

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun initObservers() {
        observeOnAddNoteResponse()
    }

    private fun openPhotoFragment(photoItem: PhotoItem){
        val intent = Intent(requireContext(), PhotoFragment::class.java)
        intent.putExtra(PhotoFragment.BUNDLE_PHOTO, photoItem)
        startActivity(intent)
    }

    override fun invoke(photoItem: PhotoItem) {
        openPhotoFragment(photoItem)
    }
}