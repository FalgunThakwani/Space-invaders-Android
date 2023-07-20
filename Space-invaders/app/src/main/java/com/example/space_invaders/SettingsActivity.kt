package com.example.space_invaders

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchHapticInvader = findViewById<Switch>(R.id.switch_haptic_invader)
        val switchHapticPlayer = findViewById<Switch>(R.id.switch_haptic_player)
        val switchSoundInGame = findViewById<Switch>(R.id.switch_sound_ingame)

        // get shared preferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // set the switch state based on the value stored in shared preferences
        switchHapticInvader.isChecked = sharedPreferences.getBoolean("HAPTIC_FEEDBACK_INVADER", true)
        switchHapticPlayer.isChecked = sharedPreferences.getBoolean("HAPTIC_FEEDBACK_PLAYER", true)
        switchSoundInGame.isChecked = sharedPreferences.getBoolean("SOUND_INGAME", true)  // In-Game Sounds preference

        // set the listener for the switches
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
    }
}