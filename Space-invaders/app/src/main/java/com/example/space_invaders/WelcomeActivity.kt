package com.example.space_invaders

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
        val intent = Intent(this, KotlinInvadersActivity::class.java)
        startActivity(intent) // Start the main activity
        finish() // Finish the login activity to prevent navigating back to it
    }
}
