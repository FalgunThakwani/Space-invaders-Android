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

    /**
     * Companion object to store constants.
     */
    companion object {
        const val PREFS_FILENAME = "com.example.space_invaders.prefs"
        const val PLAYER_NAME_KEY = "player_name_key"
    }

    private lateinit var playerNameEditText: EditText
    private lateinit var savePlayerNameButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        // Force Night Mode
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
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
            finish() // remove this activity from the stack
        }
    }

    /**
     * Saves the player's name to SharedPreferences.
     * @param name The player's name.
     */
    private fun savePlayerName(name: String) {
        // Get an instance of the SharedPreferences.Editor
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Validate the player's name
        if(!validatePlayerName(name)) {
            // Display an error message
            playerNameEditText.error = "Invalid name"
            return
        }else{
            // Store the player's name
            editor.putString(PLAYER_NAME_KEY, name)

            // Commit the changes
            editor.apply()
        }
    }

    /**
     * Validates the player's name.
     * @param name The player's name.
     */
    fun validatePlayerName(name: String): Boolean {
        // 3-12 characters
        // allowed special characters: - _ .
        // first character must be a letter
        // last character must be a letter or number
        // no consecutive special characters
        val regex = Regex("^[a-zA-Z][a-zA-Z0-9-_.]{1,10}[a-zA-Z0-9]$")
        return regex.matches(name)
    }
}
