package com.example.space_invaders.view

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.space_invaders.R

/**
 * SettingsActivity allows users to customize game settings and save player name.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the action bar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Get references to UI elements
        val switchHapticInvader = findViewById<Switch>(R.id.switch_haptic_invader)
        val switchHapticPlayer = findViewById<Switch>(R.id.switch_haptic_player)
        val switchSoundInGame = findViewById<Switch>(R.id.switch_sound_ingame)
        val nameEditText = findViewById<EditText>(R.id.editTextPlayerName2)
        val backButton = findViewById<Button>(R.id.button_setting_back)

        // Get shared preferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val playerName = getSharedPreferences(RegisterActivity.PREFS_FILENAME, Context.MODE_PRIVATE)
            .getString(RegisterActivity.PLAYER_NAME_KEY, "XYZ")

        // Set the switch state based on the value stored in shared preferences
        switchHapticInvader.isChecked = sharedPreferences.getBoolean("HAPTIC_FEEDBACK_INVADER", true)
        switchHapticPlayer.isChecked = sharedPreferences.getBoolean("HAPTIC_FEEDBACK_PLAYER", true)
        switchSoundInGame.isChecked = sharedPreferences.getBoolean("SOUND_INGAME", true)  // In-Game Sounds preference

        // Set listeners for the switches
        switchHapticInvader.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("HAPTIC_FEEDBACK_INVADER", isChecked)
                apply()
            }
        }

        switchHapticPlayer.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("HAPTIC_FEEDBACK_PLAYER", isChecked)
                apply()
            }
        }

        // Listener for the In-Game Sounds switch
        switchSoundInGame.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("SOUND_INGAME", isChecked)
                apply()
            }
        }

        // Set the player name based on the value stored in shared preferences
        nameEditText.setText(playerName)

        // Set the listener for the back button
        backButton.setOnClickListener {
            val inputName = nameEditText.text.toString()
            val regex = Regex("^[a-zA-Z][a-zA-Z0-9-_.]{1,10}[a-zA-Z0-9]$")
            if (!regex.matches(inputName)) {
                nameEditText.error = "Invalid player name"
                return@setOnClickListener
            }
            // Save the player name if changed
            if (!inputName.equals(playerName)) {
                with(this.getSharedPreferences(RegisterActivity.PREFS_FILENAME, Context.MODE_PRIVATE).edit()) {
                    putString(RegisterActivity.PLAYER_NAME_KEY, inputName)
                    apply()
                }
                Toast.makeText(this, "New name saved: $inputName", Toast.LENGTH_SHORT).show()
            }
            // Start HomeActivity and finish the current activity
            val intent = intent.setClass(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
