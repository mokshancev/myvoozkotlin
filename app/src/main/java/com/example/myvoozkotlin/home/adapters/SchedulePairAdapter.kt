package com.example.myvoozkotlin.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.example.myvoozkotlin.home.model.Lesson
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show

class SchedulePairAdapter(
    context: Context?,
    var itemLesson: List<Lesson>,
    isInfinite: Boolean,
):
    LoopingPagerAdapter<Lesson>(context, itemLesson, isInfinite) {

    fun update(lessons: List<Lesson>) {
        itemLesson = lessons
        notifyDataSetChanged()
    }

    override fun inflateView(viewType: Int, container: ViewGroup?, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_lessons, container, false)
    }

    override fun bindView(convertView: View?, listPosition: Int, viewType: Int) {
        val lesson = itemLesson[listPosition]
        convertView!!.findViewById<TextView>(R.id.tv_name)!!.text = lesson.number.toString() + ". " + lesson.name
        convertView.findViewById<TextView>(R.id.tv_type).text = lesson.typeName
        convertView!!.findViewById<TextView>(R.id.tv_fio)!!.text = lesson.teacher
        convertView!!.findViewById<TextView>(R.id.tv_place)!!.text = lesson.classroom

        if(lesson.teacher.isEmpty()){
            convertView.findViewById<View>(R.id.ivTeacher).hide()
            convertView.findViewById<View>(R.id.tv_fio).hide()
        }
        else{
            convertView.findViewById<View>(R.id.ivTeacher).show()
            convertView.findViewById<View>(R.id.tv_fio).show()
        }

        convertView.hashCode()
    }
}