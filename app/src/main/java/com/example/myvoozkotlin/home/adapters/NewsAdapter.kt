package com.example.myvoozkotlin.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.databinding.ItemStoryBinding

class NewsAdapter(private var news: List<News>): RecyclerView.Adapter<NewsAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val story = news[position]

        binding.apply {
            Glide.with(holder.itemView)
                .load(story.image)
                .into(itemImage)
            tvTitle.text = story.title
        }
    }

    fun update(news: List<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = news.size
}