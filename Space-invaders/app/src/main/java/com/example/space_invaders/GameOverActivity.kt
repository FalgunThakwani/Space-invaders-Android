package com.example.space_invaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

// Class for the GameOverActivity where players view game stats and decide next steps
class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Hide the action bar
        getSupportActionBar()?.hide()

        // Set the default night mode to night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        // Retrieve data from intent
        val highScore = intent.getIntExtra("highScore", 0)
        val score = intent.getIntExtra("score", 0)
        val waves = intent.getIntExtra("waves", 0)
        val livesUsed = intent.getIntExtra("livesUsed", 0)
        val livesLeft = intent.getIntExtra("livesLeft", 0)
        val isGameOver = intent.getBooleanExtra("isGameOver", false)

        // Bind TextViews
        val highScoreTextView = findViewById<TextView>(R.id.textView_highScore)
        val scoreTextView = findViewById<TextView>(R.id.textView_score)
        val wavesTextView = findViewById<TextView>(R.id.textView_waves)
        val livesUsedTextView = findViewById<TextView>(R.id.textView_livesUsed)
        val livesLeftTextView = findViewById<TextView>(R.id.textView_livesLeft)
        val abortTextView = findViewById<TextView>(R.id.textView_abort)

        // Bind Buttons
        val restartButton = findViewById<Button>(R.id.button_gameOver_restart)
        val homeButton = findViewById<Button>(R.id.button_gameOver_home)

        // Set the content of the TextViews
        highScoreTextView.text = "$highScore"
        scoreTextView.text = "$score"
        wavesTextView.text = "$waves"
        livesUsedTextView.text = "$livesUsed"
        livesLeftTextView.text = "$livesLeft"

        // If the game is not over, hide the abort text
        if (!isGameOver) {
            abortTextView.visibility = TextView.INVISIBLE
        }

        // When the RESTART button is clicked, navigate to the InvadersActivity
        restartButton.setOnClickListener {
            val invadersIntent = Intent(this, InvadersActivity::class.java)
            startActivity(invadersIntent)
            finish()
        }

        // When the HOME button is clicked, navigate to the HomeActivity
        homeButton.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }
}