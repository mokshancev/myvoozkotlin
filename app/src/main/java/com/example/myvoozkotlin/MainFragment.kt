package com.example.myvoozkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myvoozkotlin.databinding.FragmentMainBinding
import com.example.myvoozkotlin.helpers.adapters.ViewPagerAdapter
import com.example.myvoozkotlin.helpers.forView.LockableViewPager
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.home.LeftMenuFragment

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var adapter: ViewPagerAdapter? = null
    private var viewPagerFragment: LockableViewPager? = null

    companion object {
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
        initViewPagerAdapter()
        initLockableViewPager()

        openHomeList()
    }

    private fun initViewPagerAdapter(){
        adapter = ViewPagerAdapter(childFragmentManager)
        adapter?.addFrag(LeftMenuFragment())
        adapter?.addFrag(HomeFragment())
    }

    private fun initLockableViewPager(){
        viewPagerFragment = requireView().findViewById(R.id.vpFirstTabFragment)
        viewPagerFragment?.offscreenPageLimit = 2
        viewPagerFragment?.adapter = adapter

        viewPagerFragment?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
//                if(state == ViewPager2.SCROLL_STATE_DRAGGING){
//                    (adapter?.getItem(1) as HomeFragment).showLockBackground(true)
//                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(!positionOffset.equals(0.0F))
                    adapter?.getItem(1)!!.requireView().alpha =  0.5F*(1 + positionOffset)
            }

            override fun onPageSelected(position: Int) {
                if(adapter?.getItem(1)?.view != null){
                    if (position == 1){
                        adapter?.getItem(1)!!.requireView().alpha = 1.0F
                    }
                    else{
                        adapter?.getItem(1)!!.requireView().alpha = 0.5F
                    }
                }
            }
        })
    }

    fun openHomeList() {
        viewPagerFragment?.currentItem = 1
    }

    fun openLeftMenuList() {
        viewPagerFragment?.currentItem = 0
    }
}