package com.example.myvoozkotlin.notification.adapter

import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.databinding.ItemNotificationBinding
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show

class NotificationAdapter(var notes: MutableList<Notification>, private val itemClickListener: (Notification) -> Unit): RecyclerView.Adapter<NotificationAdapter.IntercomViewHolder>() {
    inner class IntercomViewHolder(val binding : ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val notification = notes[position]

        if(notification.photo.isEmpty()) {
            binding.ivPreview.hide()
        }
        else{
            binding.ivPreview.show()
            Glide.with(holder.itemView)
                .load(notification.photo)
                .into(binding.ivPreview)
        }

        if(notification.images.isEmpty()) {
            binding.cvImagePreview.hide()
        }
        else{
            binding.cvImagePreview.show()
            Glide.with(holder.itemView)
                .load(notification.images[0].previewPath)
                .into(binding.ivImagePreview)
        }

        binding.tvTitle.text = notification.title
        binding.tvText.text = Html.fromHtml(notification.text)
        Linkify.addLinks(binding.tvText, Linkify.ALL)
        binding.tvText.movementMethod = LinkMovementMethod.getInstance();

        binding.cvImagePreview.setOnClickListener { itemClickListener.invoke(notification) }
    }

    fun update(note: MutableList<Notification>) {
        this.notes = note
        notifyDataSetChanged()
    }

    fun addNote(note: Notification) {
        notes.add(note)
        this.notes.sortBy { it.date }
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}