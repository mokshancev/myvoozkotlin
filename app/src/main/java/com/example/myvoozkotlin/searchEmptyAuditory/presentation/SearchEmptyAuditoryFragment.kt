package com.example.myvoozkotlin.searchEmptyAuditory.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentSearchEmptyAuditoryBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.navigation.navigator
import com.example.myvoozkotlin.home.DatePickerDialogFragment
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.bendik.simplerangeview.SimpleRangeView
import java.util.*


@AndroidEntryPoint
class SearchEmptyAuditoryFragment : Fragment(), OnDatePicked {

    companion object {
        fun newInstance(): SearchEmptyAuditoryFragment {
            return SearchEmptyAuditoryFragment()
        }
    }

    private var _binding: FragmentSearchEmptyAuditoryBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var calendar: Calendar
    private lateinit var currentCalendar: Calendar
    private var idCorpus = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchEmptyAuditoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        currentCalendar = calendar.clone() as Calendar
        setDayName()

        configureViews()
        setListeners()
        initObservers()
    }

    private fun configureViews(){
        initToolbar()
        setPaddingTopMenu()
    }

    private fun setListeners(){
        setCorpusClickListener()
        setDayPickClickListener()
        setCorpusResultListener()
        setChangeRangeClickListener()
        setSearchClickListener()
    }

    private fun getCurrentUser(): AuthUserModel?{
        return userViewModel.getCurrentAuthUser()
    }

    private fun initObservers() {
        observeOnAuthResponse()
    }

    @SuppressLint("SetTextI18n")
    private fun observeOnAuthResponse() {
        userViewModel.emptyClassroomResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    if (it.data == null) {
                        userViewModel.removeCurrentUser()
                        navigator().showMainScreen()
                    }
                    else{
                        binding.tvClassrooms.text = ""
                        for ((j, post) in it.data.withIndex()) {
                            binding.apply {
                                tvClassrooms.text = tvClassrooms.text.toString() + Integer.toString(
                                    post[0].floor
                                ) + " этаж \n"
                                for ((i, postp) in post.withIndex()) {
                                    tvClassrooms.text = tvClassrooms.text
                                        .toString() + postp.floor.toString() + "-" + postp.name
                                    if (post.size == i + 1) {
                                        tvClassrooms.text = tvClassrooms.text.toString() + "."
                                    } else {
                                        tvClassrooms.text = tvClassrooms.text.toString() + ", "
                                    }
                                }
                                if (it.data.size != j + 1) {
                                    tvClassrooms.text = tvClassrooms.text.toString() + "\n\n"
                                }
                            }
                        }
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun setSearchClickListener(){
        binding.cvSearchButton.setOnClickListener {
            if(idCorpus == 0){
                UtilsUI.makeToast(getString(R.string.toast_select_corpus))
            }
            else{
                val date = calendar[Calendar.DAY_OF_MONTH].toString() + "." + calendar[Calendar.MONTH] + "." + calendar[Calendar.YEAR]
                userViewModel.getEmptyClassroom(date, idCorpus, binding.rangeView.start + 1, binding.rangeView.end + 1, userViewModel.getIdUniversity())
            }
        }
    }

    private fun setChangeRangeClickListener(){
        binding.rangeView.onTrackRangeListener = object : SimpleRangeView.OnTrackRangeListener {
            override fun onStartRangeChanged(rangeView: SimpleRangeView, start: Int) {
                binding.tvPairRange.text = "с " + (start + 1).toString() + " по " + (rangeView.end + 1).toString()
            }

            override fun onEndRangeChanged(rangeView: SimpleRangeView, end: Int) {
                binding.tvPairRange.text = "с " + (rangeView.start + 1) + " по " + (end + 1).toString()
            }
        }

        binding.rangeView.onChangeRangeListener = object : SimpleRangeView.OnChangeRangeListener {

            override fun onRangeChanged(rangeView: SimpleRangeView, start: Int, end: Int) {
                binding.tvPairRange.text = "с " + (start + 1).toString() + " по " + (end + 1).toString()
            }
        }
    }

    private fun setCorpusClickListener(){
        binding.clCorpusButton.setOnClickListener {
            openSearchFragment(SearchEnum.CORPUS.ordinal, userViewModel.getIdUniversity())
        }
    }

    private fun setCorpusResultListener(){
        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_CORPUS, this) { _, bundle ->
            val corpusName = bundle.getString(SearchFragment.KEY_FULL_NAME, "null")
            binding.tvCorpusName.text = corpusName
            idCorpus = bundle.getInt(SearchFragment.KEY_ID)
        }
    }

    private fun setDayPickClickListener(){
        binding.clDayButton.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker(){
        fragmentManager?.let {
            DatePickerDialogFragment.newInstance(calendar, this)
                .show(it, DatePickerDialogFragment.javaClass.simpleName)
        }
    }

    private fun openSearchFragment(typeSearch: Int, addParam: Int){
        val fragment = SearchFragment.newInstance(typeSearch, addParam, 1)
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.rootSearchEmptyAuditoryView, fragment, SearchFragment.javaClass.simpleName).commit()
    }

    private fun setPaddingTopMenu() {
        binding.toolbar.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun initToolbar() {
        addBackButton()
        binding.toolbar.title = getString(R.string.title_search_empty_auditory)
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDateCalendarClick(calendar: Calendar) {
        setDayName()
    }

    private fun setDayName(){
        val ii: String = Utils.getMonthName(calendar[Calendar.MONTH])
        binding.tvDayName.text = Utils.getDayName(calendar[Calendar.DAY_OF_WEEK]) + ", " + calendar[Calendar.DAY_OF_MONTH] + " " + ii + "."
    }
}