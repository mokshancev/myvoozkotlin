package com.example.myvoozkotlin.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemDayBinding
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.home.helpers.OnDayPicked
import java.util.*

class WeekAdapter(private var calendar: Calendar, private val onDayPicked: OnDayPicked): RecyclerView.Adapter<WeekAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val c = Utils.getCalendarDayOfWeek(calendar, position)

        holder.itemView.setOnClickListener {
            onDayPicked.onDayClick(position)
        }

        binding.apply {
            if(position == 6){
                tvDayName.hide()
                tvNumberName.hide()
                ivArrow.show()
                flCheck.hide()
                clContainer.background = null
                return
            }

            tvDayName.show()
            tvNumberName.show()
            ivArrow.hide()

            tvDayName.text = Utils.getDayName(position)
            tvNumberName.text = c.get(Calendar.DAY_OF_MONTH).toString()

            val resource = binding.root.resources

            if(calendar.equals(c)){
                cvStrokeBackground.setCardBackgroundColor(resource.getColor(R.color.backgroundPurple))
                tvNumberName.setTextColor(Color.WHITE)
                tvDayName.setTextColor(Color.WHITE)
                flCheck.show()
                clContainer.setBackgroundResource(R.drawable.background_stroke_day)
            }
            else{
                cvStrokeBackground.setCardBackgroundColor(resource.getColor(R.color.backgroundContent))
                tvNumberName.setTextColor(resource.getColor(R.color.textTertiary))
                tvDayName.setTextColor(resource.getColor(R.color.textSecondary))
                flCheck.hide()
                cvStrokeBackground.setCardBackgroundColor(resource.getColor(R.color.backgroundFill))
                clContainer.background = null
            }
        }
    }

    fun update(calendar: Calendar) {
        this.calendar = calendar
        notifyDataSetChanged()
    }

    override fun getItemCount() = 7
}