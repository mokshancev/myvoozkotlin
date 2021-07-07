package com.example.myvoozkotlin.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.homelibrary.model.Story

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.IntercomViewHolder>() {
    private var items = mutableListOf<Story>()

    inner class IntercomViewHolder(val binding : ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val story = items[position]

        binding.apply {
            Glide.with(holder.itemView)
                .load(story.image)
                .into(itemImage)
            tvTitle.text = story.text
        }
    }

    fun setItems(items: MutableList<Story>){
        this.items = items
    }

    override fun getItemCount() = items.size
}