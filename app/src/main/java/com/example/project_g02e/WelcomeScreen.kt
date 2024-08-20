package com.example.project_g02e

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02e.databinding.ActivityWelcomeScreenBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("LessonPreferences", MODE_PRIVATE)
        updateProgress()
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, LessonsList::class.java)
            startActivity(intent)
        }
        binding.resetButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        updateProgress()
    }
    private fun updateProgress() {
        val userName = sharedPreferences.getString("KEY_LOGGED_IN_USER", "User")
        val lessonsJson = sharedPreferences.getString("LESSONS", null)
        val lessons = if (lessonsJson != null) {
            Gson().fromJson<List<Lesson>>(lessonsJson, object : TypeToken<List<Lesson>>() {}.type)
        } else {
            emptyList()
        }
        val completedLessons = lessons.count { it.completed }
        val totalLessons = lessons.size
        val remainingLessons = totalLessons - completedLessons
        val completionPercentage = if (totalLessons > 0) completedLessons * 100 / totalLessons else 0
        binding.welcomeMessage.text = "Welcome back, $userName"
        binding.progressMessage.text = "You've completed $completionPercentage% of the course!"
        binding.completedLessonsMessage.text = "Lessons completed: $completedLessons"
        binding.remainingLessonsMessage.text = "Lessons remaining: $remainingLessons"
    }
}