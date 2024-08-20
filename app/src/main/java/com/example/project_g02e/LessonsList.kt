package com.example.project_g02e

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g02e.databinding.ActivityLessonsListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonsList : AppCompatActivity() {

    private lateinit var binding: ActivityLessonsListBinding
    private lateinit var lessonsAdapter: LessonsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var lessons: MutableList<Lesson>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("LessonPreferences", MODE_PRIVATE)
        loadLessons()
        lessonsAdapter = LessonsAdapter(lessons) { lesson ->
            val intent = Intent(this, LessonDetails::class.java)
            intent.putExtra("LESSON", lesson)
            startActivityForResult(intent, 1)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LessonsList)
            adapter = lessonsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@LessonsList,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }
    private fun loadLessons() {
        val lessonsJson = sharedPreferences.getString("LESSONS", null)
        lessons = if (lessonsJson != null) {
            Gson().fromJson(lessonsJson, object : TypeToken<MutableList<Lesson>>() {}.type)
        } else {
            mutableListOf(
                Lesson("Introduction to the Course", "12 min", false, 1, "This lesson provides an overview of the course structure, objectives, and what students can expect to learn. It sets the stage for the upcoming lessons."),
                Lesson("What is Javascript?", "30 min", false, 2, "This lesson introduces JavaScript, explaining its history, purpose, and how it fits into web development. It covers basic syntax and usage."),
                Lesson("Variables and Conditionals", "1 hr 20 min", false, 3, "This lesson dives into JavaScript variables, data types, and conditional statements. It includes practical examples and exercises to reinforce learning."),
                Lesson("Loops", "38 min", false, 4, "This lesson covers different types of loops in JavaScript, such as for, while, and do-while loops. It explains their syntax and use cases with examples."),
                Lesson("Advanced Topics", "45 min", false, 5, "This lesson explores advanced JavaScript topics, including closures, promises, and async/await. It aims to deepen understanding and improve coding skills.")
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val updatedLesson = data?.getSerializableExtra("UPDATED_LESSON") as Lesson
            val lessonIndex = lessons.indexOfFirst { it.number == updatedLesson.number }
            if (lessonIndex != -1) {
                lessons[lessonIndex] = updatedLesson
                lessonsAdapter.notifyItemChanged(lessonIndex)
            }
            val gson = Gson()
            val updatedLessonsJson = gson.toJson(lessons)
            sharedPreferences.edit().putString("LESSONS", updatedLessonsJson).apply()
        }
    }
}