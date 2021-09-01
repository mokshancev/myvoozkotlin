package com.example.myvoozkotlin.aboutNew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.aboutNew.model.OnBoardingPage
import com.example.myvoozkotlin.databinding.ItemAboutNewSliderBinding

class SliderAdapter(private val onBoardingPageList:List<OnBoardingPage>) :
    RecyclerView.Adapter<SliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAboutNewSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }

    override fun getItemCount(): Int = onBoardingPageList.size

    inner class ViewHolder(val binding: ItemAboutNewSliderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(onBoardingPage: OnBoardingPage){
            binding.apply {
                setImage(onBoardingPage.idImageResource)
                setTitle(onBoardingPage.titleResource)
                setSubTitle(onBoardingPage.subTitleResource)
            }
        }

        private fun setImage(idImageResource: Int){
            Glide.with(binding.root.context)
                .load(idImageResource)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPreview)
        }

        private fun setTitle(titleResource: Int){
            binding.tvTitle.text = itemView.resources.getString(titleResource)
        }

        private fun setSubTitle(subTitleResource: Int){
            binding.tvPostTitle.text = itemView.resources.getString(subTitleResource)
        }
    }
}