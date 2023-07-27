package com.example.space_invaders.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.space_invaders.R

class WelcomeActivity : AppCompatActivity() {

    // Delay in milliseconds for showing the splash screen
    private val SPLASH_DELAY: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the action bar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Start the main activity after the delay
        Handler().postDelayed({
            navigateToNextActivity()
        }, SPLASH_DELAY + 1500)
    }

    /**
     * Navigates to the appropriate activity based on whether the player's name is saved in SharedPreferences.
     */
    private fun navigateToNextActivity() {
        val intent: Intent

        // Check if the player's name is saved in SharedPreferences
        val sharedPrefs = getSharedPreferences(RegisterActivity.PREFS_FILENAME, Context.MODE_PRIVATE)
        if (sharedPrefs.contains(RegisterActivity.PLAYER_NAME_KEY)) {
            // Player's name is saved, navigate to HomeActivity
            intent = Intent(this, HomeActivity::class.java)
        } else {
            // Player's name is not saved, navigate to RegisterActivity
            intent = Intent(this, RegisterActivity::class.java)
        }

        // Start the next activity
        startActivity(intent)

        // Finish the welcome activity to prevent navigating back to it
        finish()
    }
}
