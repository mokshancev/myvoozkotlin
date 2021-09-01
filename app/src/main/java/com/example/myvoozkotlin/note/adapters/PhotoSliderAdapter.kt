package com.example.myvoozkotlin.note.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.aboutNew.model.OnBoardingPage
import com.example.myvoozkotlin.databinding.ItemAboutNewSliderBinding
import com.example.myvoozkotlin.databinding.ItemPhotoSliderBinding
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.note.model.Note

class PhotoSliderAdapter(private val onBoardingPageList:List<PhotoItem>, private val itemClickListener: (PhotoItem) -> Unit) :
    RecyclerView.Adapter<PhotoSliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPhotoSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])

    }

    override fun getItemCount(): Int = onBoardingPageList.size

    inner class ViewHolder(val binding: ItemPhotoSliderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photoItem: PhotoItem){
            binding.apply {
                setImage(photoItem.fullPhoto)
            }

            binding.root.setOnClickListener {
                itemClickListener.invoke(photoItem)
            }
        }

        private fun setImage(url: String){
            Glide.with(binding.root.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPreview)
        }
    }
}