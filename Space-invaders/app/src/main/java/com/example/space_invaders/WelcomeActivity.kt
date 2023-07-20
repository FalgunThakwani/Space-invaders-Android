package com.example.space_invaders

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.space_invaders.RegisterActivity.Companion.PREFS_FILENAME

class WelcomeActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val logoImageView = findViewById<ImageView>(R.id.logoImageView);

        Handler().postDelayed({
            logoImageView.visibility = View.VISIBLE
            logoImageView.animate().alpha(1f).setDuration(2000).start()
        }, SPLASH_DELAY)

        // Start the main activity after the delay
        Handler().postDelayed({
            navigateToInvaderActivity()
        }, SPLASH_DELAY + 1500)

    }

    /**
     * Navigates to the main activity.
     */
    private fun navigateToInvaderActivity() {

        val intent: Intent
        // Check if the player's name is saved in SharedPreferences
        if(getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            .contains(RegisterActivity.PLAYER_NAME_KEY)){
            intent = Intent(this, HomeActivity::class.java)
        }else{
            intent = Intent(this, RegisterActivity::class.java)
        }

        startActivity(intent) // Start the main activity
        finish() // Finish the login activity to prevent navigating back to it
    }
}
