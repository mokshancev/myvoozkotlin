package com.example.myvoozkotlin.home

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.main.presentation.MainFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentHomeBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.home.adapters.NewsAdapter
import com.example.myvoozkotlin.home.adapters.ScheduleDayAdapter
import com.example.myvoozkotlin.home.adapters.WeekAdapter
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.home.helpers.OnDayPicked
import com.example.myvoozkotlin.home.helpers.OnStoryClick
import com.example.myvoozkotlin.home.helpers.ScheduleState
import com.example.myvoozkotlin.home.viewModels.NewsViewModel
import com.example.myvoozkotlin.home.viewModels.ScheduleViewModel
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.note.NoteListFragment
import dagger.hilt.android.AndroidEntryPoint
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment(), OnDayPicked, OnDatePicked,
    OnSharedPreferenceChangeListener, OnStoryClick {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var scheduleState = ScheduleState.SHOW

    private lateinit var calendar: Calendar
    private lateinit var currentCalendar: Calendar
    private val END_SCALE = 0.8f

    companion object {
        const val WEEK_RV_ANIMATE_DURATION: Long = 250
        const val ANIMATE_TRANSITION_DURATION: Int = 300

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        BaseApp.getSharedPref().registerOnSharedPreferenceChangeListener(this)
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

        initAuthUser()

        setListeners()
    }

    private fun configureViews() {
        setHasOptionsMenu(true)
        initWeekAdapter(calendar)
        configureToolbar()
        checkDateAnother()
    }

    private fun initAuthUser(){
        loadSchedule(BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0))
        loadNews(BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0))
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
    }

    private fun loadNews(idGroup: Int) {
        if(newsViewModel.newsResponse.value != null && newsViewModel.newsResponse.value!!.data != null && newsViewModel.newsResponse.value!!.data!!.isNotEmpty()){
            initNewsAdapter(newsViewModel.newsResponse.value!!.data!!)
        }
        else
            newsViewModel.loadNews(idGroup)
    }

    private fun loadSchedule(idGroup: Int) {
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        dayOfWeek = if (dayOfWeek == 1) 6 else dayOfWeek - 2
        println("-----" + idGroup + " " + weekOfYear + " " + dayOfWeek)
        scheduleViewModel.loadScheduleDay(idGroup, weekOfYear, dayOfWeek)
    }

    private fun initNewsAdapter(news: List<News>) {
        if (binding.rvStory.adapter == null) {
            binding.rvStory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvStory.adapter = NewsAdapter(news, this)
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

    private fun checkDateAnother() {
        binding.tvDateTitle.text =
            calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + getNameWithPattern(
                calendar,
                "MMM"
            )
        val dayName = getNameWithPattern(calendar, "EEEE")
        binding.tvDayTitle.text = dayName.substring(0, 1).toUpperCase() + dayName.substring(1)
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
                loadSchedule(BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0))
            }
        }
    }

    override fun onDateCalendarClick(calendar: Calendar) {
        this.calendar = calendar
        if (binding.rvWeek.adapter != null) {
            (binding.rvWeek.adapter as WeekAdapter).update(calendar)
            endWeekRVAnimate()

            checkDateAnother()
            loadSchedule(BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0))
        }
    }



    private fun loadNoteFragment(){
        val fragment = NoteListFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, NoteListFragment.javaClass.simpleName).commit()
    }

    private fun configureToolbar() {
        setHasOptionsMenu(true)
        addBackButton()
        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_note -> loadNoteFragment()
            }
            true
        }
    }

    private fun addBackButton() {
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_user_circle)
        binding.toolbar.setNavigationOnClickListener { (parentFragment as MainFragment).openLeftMenuList() }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(Constants.APP_PREFERENCES_USER_GROUP_ID)){
            initAuthUser()
        }
        else if(key.equals(Constants.APP_PREFERENCES_AUTH_STATE)){
            initAuthUser()
        }
    }

    override fun onStoryClick(stories: News) {
        val myStories = ArrayList<MyStory>()
        for (story in stories.stories) {
            myStories.add(
                MyStory(
                    story.image,
                    Calendar.getInstance().time
                )
            )
            println("----" + story.image)
        }
        StoryView.Builder(requireActivity().getSupportFragmentManager())
            .setStoriesList(myStories)
            .setStoryDuration(5000)
            .setTitleText(stories.name)
            .setTitleLogoUrl(stories.logoImage)
            .setSubtitleText("Медиацентр")
            .setStoryClickListeners(object : StoryClickListeners {
                override fun onDescriptionClickListener(position: Int) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(stories.link))
                    startActivity(browserIntent)
                }

                override fun onTitleIconClickListener(position: Int) {}
            })
            .setOnStoryChangedCallback {
                //Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
            .setStartingIndex(0)
            .build()
            .show()
    }
}