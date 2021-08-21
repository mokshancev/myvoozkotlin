package com.example.myvoozkotlin.home.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.databinding.ItemStoryBinding
import com.example.myvoozkotlin.home.helpers.OnStoryClick
import com.example.myvoozkotlin.models.news.News
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.util.*

class NewsAdapter(private var news: List<News>, private var onStoryClick: OnStoryClick): RecyclerView.Adapter<NewsAdapter.IntercomViewHolder>() {

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
            Glide.with(holder.itemView)
                .load(story.logoImage)
                .into(ivUnion)
            tvTitle.text = story.title
        }

        binding.root.setOnClickListener {
            onStoryClick.onStoryClick(news[position])
        }
    }

    fun update(news: List<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = news.size
}