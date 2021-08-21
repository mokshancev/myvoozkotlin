package com.example.myvoozkotlin.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentMainBinding
import com.example.myvoozkotlin.main.adapters.ViewPagerAdapter
import com.example.myvoozkotlin.helpers.forView.LockableViewPager
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.leftMenu.presentation.LeftMenuFragment
import com.example.myvoozkotlin.main.helpers.enums.MainPagesEnum

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var adapter: ViewPagerAdapter? = null
    private var viewPagerFragment: LockableViewPager? = null

    companion object {
        const val START_ALPHA = 1.0f
        const val END_ALPHA = 0.5f
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        openHomeList()
    }

    private fun configureViews(){
        configureViewPagerAdapter()
        configureLockableViewPager()
    }

    private fun configureViewPagerAdapter(){
        viewPagerFragment = requireView().findViewById(R.id.vpFirstTabFragment)
        viewPagerFragment?.offscreenPageLimit = MainPagesEnum.values().size
        if(viewPagerFragment!!.adapter == null){
            adapter = ViewPagerAdapter(childFragmentManager)
            adapter?.addFrag(LeftMenuFragment())
            adapter?.addFrag(HomeFragment())
            viewPagerFragment?.adapter = adapter
        }
    }

    private fun configureLockableViewPager(){
        viewPagerFragment?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(!positionOffset.equals(0.0F))
                    adapter?.getItem(MainPagesEnum.HOME_FRAGMENT.ordinal)!!.requireView().alpha =  END_ALPHA*(START_ALPHA + positionOffset)
            }

            override fun onPageSelected(position: Int) {
                if(adapter?.getItem(MainPagesEnum.HOME_FRAGMENT.ordinal)?.view != null){
                    if (position == MainPagesEnum.HOME_FRAGMENT.ordinal){
                        adapter?.getItem(MainPagesEnum.HOME_FRAGMENT.ordinal)!!.requireView().alpha = START_ALPHA
                    }
                    else{
                        adapter?.getItem(MainPagesEnum.HOME_FRAGMENT.ordinal)!!.requireView().alpha = END_ALPHA
                    }
                }
            }
        })
    }

    fun openHomeList() {
        viewPagerFragment?.currentItem = MainPagesEnum.HOME_FRAGMENT.ordinal
    }

    fun openLeftMenuList() {
        viewPagerFragment?.currentItem = MainPagesEnum.LEFT_MENU_FRAGMENT.ordinal
    }
}