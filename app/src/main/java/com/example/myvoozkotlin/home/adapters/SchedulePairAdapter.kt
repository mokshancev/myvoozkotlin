package com.example.myvoozkotlin.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.myvoozkotlin.models.news.News

class SchedulePairAdapter(
    context: Context?,
    itemList: List<Lesson>,
    isInfinite: Boolean,
):
    LoopingPagerAdapter<Lesson>(context, itemList, isInfinite) {

    fun update(news: List<Lesson>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun inflateView(viewType: Int, container: ViewGroup?, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_lessons, container, false)
    }

    override fun bindView(convertView: View?, listPosition: Int, viewType: Int) {
        //val containerCV: CardView = convertView!!.findViewById(R.id.cv_container)
    }
}