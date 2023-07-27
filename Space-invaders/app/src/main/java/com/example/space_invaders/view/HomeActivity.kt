package com.example.space_invaders.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.space_invaders.R

// Class for the HomeActivity where players start or navigate to different sections of the game
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Hide the action bar
        getSupportActionBar()?.hide()

        // Force Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        // Bind UI elements to variables
        val welcomeTextView = findViewById<TextView>(R.id.textView_homeWelcome)
        val startGameButton = findViewById<Button>(R.id.button_startGame)
        val dockyardButton = findViewById<Button>(R.id.button_dockyard)
        val tutorialButton = findViewById<Button>(R.id.button_tutorial)
        val settingsButton = findViewById<Button>(R.id.button_settings)

        // Retrieve the player's name from shared preferences
        val playerNamePreferences = getSharedPreferences(RegisterActivity.PREFS_FILENAME, Context.MODE_PRIVATE)
        val playerName = playerNamePreferences.getString(RegisterActivity.PLAYER_NAME_KEY, "XYZ")

        // Update the welcome text view with player's name
        welcomeTextView.text = "Welcome, Captain $playerName"

        // Navigate to InvadersActivity when the "START" button is clicked
        startGameButton.setOnClickListener {
            val invadersIntent = Intent(this, InvadersActivity::class.java)
            startActivity(invadersIntent)
            finish()
        }

        // Navigate to DockYard when the "DOCKYARD" button is clicked
        dockyardButton.setOnClickListener {
            val dockYardIntent = Intent(this, DockYard::class.java)
            startActivity(dockYardIntent)
            finish()
        }

        // Navigate to TutorialActivity when the "TUTORIAL" button is clicked
        tutorialButton.setOnClickListener{
            val tutorialIntent = Intent(this, TutorialActivity::class.java)
            startActivity(tutorialIntent)
            finish()
        }

        // Navigate to SettingsActivity when the "SETTINGS" button is clicked
        settingsButton.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            finish()
        }
    }
}