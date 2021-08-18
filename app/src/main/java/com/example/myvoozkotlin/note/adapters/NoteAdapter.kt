package com.example.myvoozkotlin.note.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.ItemNoteBinding
import com.example.myvoozkotlin.helpers.Utils
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.models.Note
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.IntercomViewHolder>() {
    private var notes: MutableList<Note> = mutableListOf()

    inner class IntercomViewHolder(val binding : ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val note = notes[position]

        holder.itemView.setOnClickListener {

        }

        if(note.images.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(note.images[0].fullPhoto)
                .into(binding.ivPreview)
            binding.cvPreviewContainer.show()
            if(note.images.size > 1){
                binding.cvAddImagesContainer.show()
                binding.tvAddImages.text = "+" + (note.images.size-1).toString()
            }
            else{
                binding.cvAddImagesContainer.hide()
            }
        }
        else{
            binding.cvPreviewContainer.hide()
            binding.cvAddImagesContainer.hide()
        }

        binding.apply {
            tvShortText.text = note.name
            tvLongText.text = note.text
            tvObject.text = note.nameObject
            if(note.markMe){
                ivMark.show()
            }
            else{
                ivMark.hide()
            }

            val cal_next_day = Calendar.getInstance()
            cal_next_day.timeInMillis = System.currentTimeMillis()
            cal_next_day.add(Calendar.DATE, 1)

            val cal_cur = Calendar.getInstance()
            cal_cur.timeInMillis = System.currentTimeMillis()

            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            try {
                calendar.time = sdf.parse(note.date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val ii: String = Utils.getMonthName(calendar[Calendar.MONTH])
            val minutes =
                if (calendar[Calendar.MINUTE] < 10) "0" + calendar[Calendar.MINUTE] else calendar[Calendar.MINUTE].toString()

            if (cal_cur.after(calendar)) {
                val mm =
                    if (calendar[Calendar.MONTH] < 10) "0" + calendar[Calendar.MONTH] else calendar[Calendar.MONTH].toString()
                binding.tvDate.setText("Истек, " + calendar[Calendar.DAY_OF_MONTH] + " " + ii + " в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes)

            } else {
                if (cal_next_day.after(calendar)) {
                    if (cal_cur[Calendar.DAY_OF_YEAR] == calendar[Calendar.DAY_OF_YEAR]) {
                        binding.tvDate.setText("Сегодня в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes)
                    } else {
                        binding.tvDate.setText("Завтра в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes)
                    }
                } else {
                    binding.tvDate.setText(
                        Utils.getDayName(calendar[Calendar.DAY_OF_WEEK])
                            .toString() + ", " + calendar[Calendar.DAY_OF_MONTH] + " " + ii + " в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes
                    )
                }
            }
        }
    }

    fun update(note: MutableList<Note>) {
        this.notes = note
        this.notes.sortBy { it.date }
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        notes.add(note)
        this.notes.sortBy { it.date }
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}