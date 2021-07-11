package com.example.myvoozkotlin.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingViewPager
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.R
import com.rd.PageIndicatorView


class ScheduleDayAdapter(val context: Context, private var news: List<List<Lesson>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_EMPTY = 1

    var lessons: List<List<Lesson>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            ItemPairLessonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pair_lesson, parent, false))
        else
            ItemPairLessonEmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pair_lesson_empty, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (lessons[position].isEmpty()) {
            (holder as ItemPairLessonEmptyViewHolder).bind(position)
        } else {
            (holder as ItemPairLessonViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (lessons[position].isEmpty()) {
            VIEW_TYPE_EMPTY
        } else VIEW_TYPE_NORMAL
    }

    fun update(news: List<List<Lesson>>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = news.size

    private inner class ItemPairLessonEmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }

        fun bind(position: Int) {

        }
    }

    private inner class ItemPairLessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewPager: LoopingViewPager = itemView.findViewById(R.id.viewpager)
        private val indicatorView: PageIndicatorView = itemView.findViewById(R.id.indicator)
        private val firstTimeTV: TextView = itemView.findViewById(R.id.tv_first_time)
        private val secondTimeTV: TextView = itemView.findViewById(R.id.tv_last_time)
        val adapter = SchedulePairAdapter(itemView.context, lessons.get(position), false)

        init {

        }

        fun bind(position: Int) {
            viewPager.adapter = adapter
            indicatorView.count = viewPager.indicatorCount
            viewPager.setIndicatorPageChangeListener(object :
                LoopingViewPager.IndicatorPageChangeListener {
                override fun onIndicatorProgress(selectingPosition: Int, progress: Float) {
                    indicatorView.setProgress(selectingPosition, progress)
                }

                override fun onIndicatorPageChange(newIndicatorPosition: Int) {
                    indicatorView.selection = newIndicatorPosition
                }

            })
        }
    }
}