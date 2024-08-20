package com.example.project_g02e

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02e.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("LessonPreferences", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        if (!isFirstTime) {
            navigateToWelcomeScreen()
        }
        binding.continueButton.setOnClickListener {
            val userName = binding.editTextText.text.toString()
            if (userName.isEmpty()) {
                val snackbar = Snackbar.make(binding.root, "Name cannot be empty", Snackbar.LENGTH_SHORT)
                snackbar.show()
            } else {
                sharedPreferences.edit().putString("KEY_LOGGED_IN_USER", userName).apply()
                sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
                navigateToWelcomeScreen()
            }
        }
    }
    private fun navigateToWelcomeScreen() {
        val intent = Intent(this, WelcomeScreen::class.java)
        startActivity(intent)
        finish()
    }
}