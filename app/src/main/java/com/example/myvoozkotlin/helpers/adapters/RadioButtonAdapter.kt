package com.example.myvoozkotlin.helpers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemRadioButtonBinding
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.models.RadioItem
import com.example.myvoozkotlin.search.helpers.OnSearchItemPicked

class RadioButtonAdapter(private var radioItems: List<RadioItem>, private val onSearchItemPicked: OnSearchItemPicked): RecyclerView.Adapter<RadioButtonAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemRadioButtonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemRadioButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val item = radioItems[position]

        binding.tvTitle.text = item.text
        if(item.isActive){
            binding.cvMainBackground.setCardBackgroundColor(holder.itemView.resources.getColor(R.color.textLink))
            binding.tvTitle.setTextColor(holder.itemView.resources.getColor(R.color.white))
        }
        else{
            binding.cvMainBackground.setCardBackgroundColor(holder.itemView.resources.getColor(R.color.backgroundFill))
            binding.tvTitle.setTextColor(holder.itemView.resources.getColor(R.color.textPrimary))
        }

        holder.itemView.setOnClickListener {
                radioItems.forEachIndexed { index, radioItem ->
                    radioItem.isActive = false
                    if(index == position){
                        radioItem.isActive = true
                }
            }
            notifyDataSetChanged()
        }
    }

    fun update(radioItems: List<RadioItem>) {
        this.radioItems = radioItems
        notifyDataSetChanged()
    }

    fun getSelectItemPosition(): Int {
        radioItems.forEachIndexed { index, radioItem ->
            if(radioItem.isActive)
                return index
        }
        return -1
    }

    override fun getItemCount() = radioItems.size
}