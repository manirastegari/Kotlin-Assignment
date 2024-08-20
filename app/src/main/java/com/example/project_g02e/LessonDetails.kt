package com.example.project_g02e

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02e.databinding.ActivityLessonDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonDetails : AppCompatActivity() {

    private lateinit var binding: ActivityLessonDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("LessonPreferences", MODE_PRIVATE)
        val lesson = intent.getSerializableExtra("LESSON") as Lesson
        binding.lessonName.text = "${lesson.number}. ${lesson.name}"
        binding.lessonLength.text = "Length: ${lesson.length}"
        binding.lessonDetails.text = lesson.details
        binding.watchLessonButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=W6NZfCO5SIk"))
            startActivity(intent)
        }
        binding.markCompleteButton.setOnClickListener {
            // change the value of lesson.completed when user push the button
            lesson.completed = !lesson.completed
            val gson = Gson()
            val lessonsJson = sharedPreferences.getString("LESSONS", null)
            val lessons = if (lessonsJson != null) {
                gson.fromJson<MutableList<Lesson>>(lessonsJson, object : TypeToken<MutableList<Lesson>>() {}.type)
            } else {
                mutableListOf()
            }

            val lessonIndex = lessons.indexOfFirst { it.number == lesson.number }
            if (lessonIndex != -1) {
                lessons[lessonIndex] = lesson
            } else {
                lessons.add(lesson)
            }
            val updatedLessonsJson = gson.toJson(lessons)
            sharedPreferences.edit().putString("LESSONS", updatedLessonsJson).apply()
            val completedLessons = lessons.count { it.completed }
            sharedPreferences.edit().putInt("COMPLETED_LESSONS", completedLessons).apply()
            val resultIntent = Intent()
            resultIntent.putExtra("UPDATED_LESSON", lesson)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}