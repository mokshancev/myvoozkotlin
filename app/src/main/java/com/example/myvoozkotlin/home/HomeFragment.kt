package com.example.myvoozkotlin.home

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentHomeBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.home.adapters.NewsAdapter
import com.example.myvoozkotlin.home.adapters.ScheduleDayAdapter
import com.example.myvoozkotlin.home.adapters.WeekAdapter
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.home.helpers.OnDayPicked
import com.example.myvoozkotlin.home.helpers.ScheduleState
import com.example.myvoozkotlin.home.viewModels.NewsViewModel
import com.example.myvoozkotlin.home.viewModels.ScheduleViewModel
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.selectGroup.SelectGroupFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment(), OnDayPicked, OnDatePicked,
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var scheduleState = ScheduleState.SHOW

    private lateinit var calendar: Calendar
    private lateinit var currentCalendar: Calendar
    private val END_SCALE = 0.8f

    companion object {
        const val WEEK_RV_ANIMATE_DURATION: Long = 250

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (null != savedInstanceState) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun onMyButtonClick(view: View?) {
        // выводим сообщение
        Toast.makeText(requireContext(), "Зачем вы нажали?", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        currentCalendar = calendar.clone() as Calendar

        configureViews()

        initObservers()

        loadSchedule()
        loadNews()

        if (AuthorizationState.UNAUTORIZATE.ordinal == BaseApp.getAuthState()) {
            binding.navigationView.getHeaderView(0)
                .findViewById<View>(R.id.ll_notification_setting_button).hide()
            binding.navigationView.getHeaderView(0)
                .findViewById<View>(R.id.ll_profile_setting_button).hide()
            binding.navigationView.getHeaderView(0)
                .findViewById<View>(R.id.ll_select_group).setOnClickListener {
                    loadSelectGroupFragment()
                    binding.drawerLayour.close()
                }
        } else if (AuthorizationState.AUTORIZATE.equals(BaseApp.getAuthState()) || AuthorizationState.GROUP_AUTORIZATE.equals(
                BaseApp.getAuthState()
            )
        ) {

        }

        setListeners()
    }

    private fun configureViews() {
        setHasOptionsMenu(true)
        initWeekAdapter(calendar)
        configureToolbar()
        animateNavigationDrawer()
    }

    private fun setListeners() {
        binding.cvShowEmptyLessonsButton.setOnClickListener {
            if (scheduleState == ScheduleState.SHOW) {
                binding.ivShowEmptyLessons.setImageResource(R.drawable.ic_eye_slash)
                scheduleState = ScheduleState.HIDE
            } else {
                binding.ivShowEmptyLessons.setImageResource(R.drawable.ic_eye)
                scheduleState = ScheduleState.SHOW
            }
        }

        binding.navigationView.setOnClickListener { binding.drawerLayour.close() }

        binding.navigationView.getHeaderView(0)
            .findViewById<View>(R.id.ll_vk_social_button).setOnClickListener {
                openLink(Constants.APP_PREFERENCES_VK_SOCIAL_LINK)
                binding.drawerLayour.close()
            }

        binding.navigationView.getHeaderView(0)
            .findViewById<View>(R.id.ll_autorization_button).setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_authFragment)
                binding.drawerLayour.close()
            }

        binding.navigationView.getHeaderView(0)
            .findViewById<View>(R.id.cv_close_button).setOnClickListener {
                binding.drawerLayour.close()
            }

        binding.cvDateButton.setOnClickListener {
            if (binding.rvWeek.isVisible) {
                binding.apply {
                    rvWeek.clearAnimation()
                    rvWeek.hide()
                }
            } else {
                binding.rvWeek.show()
                startWeekRVAnimate()
            }
        }

        BaseApp.getSharedPref().registerOnSharedPreferenceChangeListener(this)
    }

    private fun loadNews() {
        if(newsViewModel.newsResponse.value != null && newsViewModel.newsResponse.value!!.data != null && newsViewModel.newsResponse.value!!.data!!.isNotEmpty()){
            initNewsAdapter(newsViewModel.newsResponse.value!!.data!!)
        }
        else
            newsViewModel.loadNews(10)
    }

    private fun loadSchedule() {
        if(scheduleViewModel.scheduleDayResponse.value != null && scheduleViewModel.scheduleDayResponse.value!!.data != null && scheduleViewModel.scheduleDayResponse.value!!.data!!.isNotEmpty()){
            initScheduleDayAdapter(scheduleViewModel.scheduleDayResponse.value!!.data!!)
        }
        else{
            val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            dayOfWeek = if (dayOfWeek == 1) 6 else dayOfWeek - 2
            scheduleViewModel.loadScheduleDay(10, weekOfYear, dayOfWeek)
        }
    }

    private fun animateNavigationDrawer() {
        binding.drawerLayour.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                binding.drawerLayour.setScrimColor(0xbba1a4a5.toInt())

                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = binding.llContent.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                binding.llContent.translationX = xTranslation
            }
        })
    }

    private fun initNewsAdapter(news: List<News>) {
        if (binding.rvStory.adapter == null) {
            binding.rvStory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvStory.adapter = NewsAdapter(news)
        } else {
            (binding.rvStory.adapter as? NewsAdapter)?.update(news)
        }
    }

    private fun initScheduleDayAdapter(lessons: List<List<Lesson>>) {
        if (binding.rvLesson.adapter == null) {
            binding.rvLesson.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvLesson.adapter = ScheduleDayAdapter(requireContext(), lessons)
        } else {
            (binding.rvLesson.adapter as? ScheduleDayAdapter)?.update(lessons)
        }
    }

    private fun initWeekAdapter(calendar: Calendar) {
        if (binding.rvWeek.adapter == null) {
            binding.rvWeek.layoutManager = GridLayoutManager(requireContext(), 7)
            binding.rvWeek.adapter = WeekAdapter(calendar, this)
        } else {
            (binding.rvWeek.adapter as? WeekAdapter)?.update(calendar)
        }
    }

    private fun initObservers() {
        observeOnNewsResponse()
        observeOnScheduleDayResponse()
    }

    private fun observeOnNewsResponse() {
        newsViewModel.newsResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.newsShimmerView.show()
                    binding.newsShimmerView.startShimmer()
                    binding.rvStory.hide()
                }
                Status.SUCCESS -> {
                    binding.rvStory.show()
                    binding.newsShimmerView.hide()
                    binding.newsShimmerView.stopShimmer()

                    if (it.data == null) {

                    } else {
                        initNewsAdapter(it.data)
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun observeOnScheduleDayResponse() {
        scheduleViewModel.scheduleDayResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.show()
                    binding.rvLesson.hide()
                    (binding.root.findViewById(R.id.ll_empty) as View).hide()
                }
                Status.SUCCESS -> {
                    binding.progressBar.hide()

                    if (it.data == null) {

                    } else {
                        initScheduleDayAdapter(it.data)
                        if (it.data.isEmpty()) {
                            binding.rvLesson.hide()
                            (binding.root.findViewById(R.id.ll_empty) as View).show()
                        } else {
                            binding.rvLesson.show()
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

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun checkDateAnother() {
        if (calendar.equals(currentCalendar)) {
            binding.apply {
                tvDateTitle.hide()
                cvDateChangeIndicator.hide()
            }
        } else {
            binding.apply {
                tvDateTitle.show()
                cvDateChangeIndicator.show()
                binding.tvDateTitle.text =
                    calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + getNameWithPattern(
                        calendar,
                        "MMM"
                    )
            }
        }
        val dayName = getNameWithPattern(calendar, "EEEE")
        binding.tvDateTitle.text = dayName.substring(0, 1).toUpperCase() + dayName.substring(1)
    }

    private fun getNameWithPattern(calendar: Calendar, pattern: String): String {
        val date = SimpleDateFormat(pattern)
        return date.format(calendar.time)
    }

    private fun startWeekRVAnimate() {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = WEEK_RV_ANIMATE_DURATION
        animation.fillAfter = true
        binding.rvWeek.startAnimation(animation)
    }

    private fun endWeekRVAnimate() {
        binding.rvWeek.clearAnimation()
        binding.rvWeek.hide()
    }

    override fun onDayClick(position: Int) {
        if (position == 6) {
            fragmentManager?.let {
                DatePickerDialogFragment.newInstance(calendar, this)
                    .show(it, DatePickerDialogFragment.javaClass.simpleName)
            }
        } else {
            calendar = Utils.getCalendarDayOfWeek(calendar, position)
            if (binding.rvWeek.adapter != null) {
                (binding.rvWeek.adapter as? WeekAdapter)!!.update(calendar)
                endWeekRVAnimate()

                checkDateAnother()
                loadSchedule()
            }
        }
    }

    override fun onDateCalendarClick(calendar: Calendar) {
        this.calendar = calendar
        if (binding.rvWeek.adapter != null) {
            (binding.rvWeek.adapter as WeekAdapter).update(calendar)
            endWeekRVAnimate()

            checkDateAnother()
            loadSchedule()
        }
    }

    private fun loadSelectGroupFragment(){
        val fragment = SelectGroupFragment.newInstance()
        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootViewMain, fragment, SelectGroupFragment.javaClass.simpleName).commit()
    }

    private fun configureToolbar() {
        setHasOptionsMenu(true)
        addBackButton()
        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_note -> Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_noteFragment)
            }
            true
        }
    }

    private fun addBackButton() {
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_user_circle)
        binding.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                binding.drawerLayour.open()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_note) {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_homeFragment_to_noteFragment)
        }
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("keykey", key.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.getSharedPref().unregisterOnSharedPreferenceChangeListener(this)
    }
}