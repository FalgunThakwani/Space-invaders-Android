package com.example.space_invaders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.space_invaders.RegisterActivity.Companion.PREFS_FILENAME

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_home)

        val textViewHomeWelcome = findViewById<TextView>(R.id.textView_homeWelcome)
        val startButton = findViewById<Button>(R.id.button_startGame)
        val dockyardButton = findViewById<Button>(R.id.button_dockyard)
        val settingsButton = findViewById<Button>(R.id.button_settings)

        // Retrieve the player's name
        val playerName = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            .getString(RegisterActivity.PLAYER_NAME_KEY, "XYZ")

        // Update the TextView
        textViewHomeWelcome.text = "Welcome, Captain $playerName"

        // Navigate to KotlinInvadersActivity when the "START" button is clicked
        startButton.setOnClickListener {
            val intent = Intent(this, KotlinInvadersActivity::class.java)
            startActivity(intent)
        }

        /*// Navigate to DockyardActivity when the "DOCKYARD" button is clicked
        dockyardButton.setOnClickListener {
            val intent = Intent(this, DockyardActivity::class.java)
            startActivity(intent)
        }

        // Navigate to SettingsActivity when the "SETTINGS" button is clicked
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }*/
    }
}
