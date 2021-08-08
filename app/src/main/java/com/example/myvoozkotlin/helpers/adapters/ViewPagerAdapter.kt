package com.example.myvoozkotlin.helpers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(manager: FragmentManager?) : FragmentStatePagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    fun replaceFrag(position: Int, fragment: Fragment) {
        mFragmentList.removeAt(position)
        mFragmentList.add(position, fragment)
    }

    override fun getPageWidth(position: Int): Float {
        if(position == 0)
            return 0.85F
        return 1F
    }
}