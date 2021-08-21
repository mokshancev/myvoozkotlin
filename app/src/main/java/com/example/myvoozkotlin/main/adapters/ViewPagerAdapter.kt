package com.example.myvoozkotlin.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.myvoozkotlin.main.helpers.enums.MainPagesEnum
import java.util.ArrayList

class ViewPagerAdapter(manager: FragmentManager?) : FragmentStatePagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()

    companion object{
        const val LEFT_MENU_PAGE_WIDTH = 0.85F
        const val SECOND_PAGE_WIDTH = 1.0F
    }

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

    override fun getPageWidth(position: Int): Float {
        if(position == MainPagesEnum.LEFT_MENU_FRAGMENT.ordinal)
            return LEFT_MENU_PAGE_WIDTH
        return SECOND_PAGE_WIDTH
    }
}