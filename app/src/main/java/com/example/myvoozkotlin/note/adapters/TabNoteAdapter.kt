package com.example.myvoozkotlin.note.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.myvoozkotlin.databinding.ItemTabitemBinding
import com.example.myvoozkotlin.home.helpers.OnTabItemPicked
import com.example.myvoozkotlin.models.TabItem

class TabNoteAdapter(private var tabItems: List<TabItem>, private val onTabItemPicked: OnTabItemPicked): RecyclerView.Adapter<TabNoteAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemTabitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemTabitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val tabItem = tabItems[position]

        binding.tvTitle.text = tabItem.name
        binding.tvCount.text = tabItem.count.toString()
        if(tabItem.isActive){
            binding.cvContainer.setCardBackgroundColor(holder.itemView.resources.getColor(R.color.backgroundLink))
            binding.tvTitle.setTextColor(holder.itemView.resources.getColor(R.color.white))
            binding.tvCount.setTextColor(holder.itemView.resources.getColor(R.color.white))
        }
        else{
            binding.cvContainer.setCardBackgroundColor(holder.itemView.resources.getColor(R.color.backgroundFill))
            binding.tvTitle.setTextColor(holder.itemView.resources.getColor(R.color.textSecondary))
            binding.tvCount.setTextColor(holder.itemView.resources.getColor(R.color.textTertiary))
        }
    }

    fun update(news: List<TabItem>) {
        this.tabItems = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = tabItems.size
}