package com.example.space_invaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the action bar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)

        // Set the night mode to automatically switch to dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        // Set the layout for the activity
        setContentView(R.layout.activity_tutorial)

        // Set up the "CONTINUE" button click listener
        val continueButton = findViewById<Button>(R.id.button_continue)
        continueButton.setOnClickListener {
            navigateToHomeActivity()
        }
    }

    /**
     * Navigate to the HomeActivity and finish the current TutorialActivity.
     */
    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
