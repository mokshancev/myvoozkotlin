package com.example.myvoozkotlin.notification.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myvoozkotlin.models.TabItem
import com.example.myvoozkotlin.notification.presentation.NotificationBaseTabFragment


class NotificationViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabItems: MutableList<TabItem>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return NotificationBaseTabFragment.newInstance(tabItems[position].count)
    }

    override fun getItemCount(): Int {
        return tabItems.size
    }
}