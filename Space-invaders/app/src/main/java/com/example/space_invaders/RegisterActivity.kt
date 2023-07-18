package com.example.space_invaders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val PREFS_FILENAME = "com.example.space_invaders.prefs"
        const val PLAYER_NAME_KEY = "player_name_key"
    }

    private lateinit var playerNameEditText: EditText
    private lateinit var savePlayerNameButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_register)

        // Initialize views
        playerNameEditText = findViewById(R.id.editTextTextPersonName)
        savePlayerNameButton = findViewById(R.id.button_savePlayerName)

        // Initialize shared preferences
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        // Set an on-click listener to the save button
        savePlayerNameButton.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            savePlayerName(playerName)

            // Navigate to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // remove this activity from the stack
        }
    }

    private fun savePlayerName(name: String) {
        // Get an instance of the SharedPreferences.Editor
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // TODO: Validate the player's name
        // Store the player's name
        editor.putString(PLAYER_NAME_KEY, name)

        // Commit the changes
        editor.apply()
    }
}
