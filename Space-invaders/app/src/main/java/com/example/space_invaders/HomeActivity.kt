package com.example.space_invaders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textViewHomeWelcome = findViewById<TextView>(R.id.textView_homeWelcome)
        val startButton = findViewById<Button>(R.id.button_startGame)
        val dockyardButton = findViewById<Button>(R.id.button_dockyard)
        val settingsButton = findViewById<Button>(R.id.button_settings)

        // Retrieve the player's name
        val sharedPref = getSharedPreferences("PlayerInfo", Context.MODE_PRIVATE)
        val playerName = sharedPref.getString("playerName", "Captain ")

        // Update the TextView
        textViewHomeWelcome.text = "Welcome, $playerName"

        // Navigate to KotlinInvadersActivity when the "START" button is clicked
        startButton.setOnClickListener {
            val intent = Intent(this, KotlinInvadersActivity::class.java)
            startActivity(intent)
        }

        // Navigate to SettingsActivity when the "SETTINGS" button is clicked
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
