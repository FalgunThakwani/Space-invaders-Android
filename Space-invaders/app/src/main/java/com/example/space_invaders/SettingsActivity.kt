package com.example.space_invaders

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchHaptic = findViewById<Switch>(R.id.switch_haptic)

        // get shared preferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // set the switch state based on the value stored in shared preferences
        switchHaptic.isChecked = sharedPreferences.getBoolean("HAPTIC_FEEDBACK_ENABLED", true)

        // set the listener for the switch
        switchHaptic.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("HAPTIC_FEEDBACK_ENABLED", isChecked)
                apply()
            }
        }
    }
}