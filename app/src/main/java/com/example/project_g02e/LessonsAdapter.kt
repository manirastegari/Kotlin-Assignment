package com.example.project_g02e

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g02e.databinding.ItemLessonLayoutBinding

class LessonsAdapter(
    private val lessons: List<Lesson>,
    private val onLessonClick: (Lesson) -> Unit
) : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }
    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.bind(lesson)
    }
    override fun getItemCount(): Int = lessons.size
    inner class LessonViewHolder(val binding: ItemLessonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson) {
            binding.lessonNumber.text = lesson.number.toString()
            binding.lessonName.text = lesson.name
            binding.lessonLength.text = lesson.length
            binding.root.setOnClickListener { onLessonClick(lesson) }
            binding.lessonCompleted.setImageResource(
                if (lesson.completed) {
                    R.drawable.done
                } else {
                    0
                }
            )
        }
    }
}