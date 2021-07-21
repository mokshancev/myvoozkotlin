package com.example.myvoozkotlin.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.databinding.ItemSearchBinding
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.search.helpers.OnSearchItemPicked

class SearchAdapter(private var searchItems: List<SearchItem>, private val onSearchItemPicked: OnSearchItemPicked): RecyclerView.Adapter<SearchAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val item = searchItems[position]

        binding.apply {
            if(item.fullName.isNullOrEmpty())
                tvTitle.text = item.name
            else
                tvTitle.text = item.fullName
        }

        holder.itemView.setOnClickListener {
            onSearchItemPicked.onSearchItemClick(item)
        }
    }

    fun update(news: List<SearchItem>) {
        this.searchItems = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = searchItems.size
}