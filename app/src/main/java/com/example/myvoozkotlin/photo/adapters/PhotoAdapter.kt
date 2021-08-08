package com.example.myvoozkotlin.photo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemPhotoBinding
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.note.helpers.OnPhotoPicked

class PhotoAdapter(private val photoPicked: OnPhotoPicked): RecyclerView.Adapter<PhotoAdapter.IntercomViewHolder>() {
    private var items: MutableList<PhotoItem> = arrayListOf<PhotoItem>()
    inner class IntercomViewHolder(val binding : ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        const val ANIMATE_TRANSITION_DURATION: Int = 300
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val item = items[position]

        holder.itemView.setOnClickListener {
            photoPicked.onPhotoClick(position)
        }

        Glide.with(BaseApp.instance!!)
            .load(item.previewPath)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(ANIMATE_TRANSITION_DURATION))
            .into(binding.ivPhoto)
    }

    fun update(items: MutableList<PhotoItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addItem(item: PhotoItem){
        items.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): PhotoItem{
        return items[position]
    }

    fun getItems(): List<Int>{
        val idPhotoItems = mutableListOf<Int>()
        items.forEach {
            println("fwfwefwef" + it)
            idPhotoItems.add(it.id)
        }
        return idPhotoItems
    }

    override fun getItemCount() = items.size
}