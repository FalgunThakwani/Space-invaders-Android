package com.example.space_invaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_game_over)

        // retrive data from intent
        val highScore = intent.getIntExtra("highScore", 0)
        val score = intent.getIntExtra("score", 0)
        val waves = intent.getIntExtra("waves", 0)
        val livesUsed = intent.getIntExtra("livesUsed", 0)
        val livesLeft = intent.getIntExtra("livesLeft", 0)
        val isGameOver = intent.getBooleanExtra("isGameOver", true)

        val highScoreTextView = findViewById<TextView>(R.id.textView_highScore)
        val scoreTextView = findViewById<TextView>(R.id.textView_score)
        val wavesTextView = findViewById<TextView>(R.id.textView_waves)
        val livesUsedTextView = findViewById<TextView>(R.id.textView_livesUsed)
        val livesLeftTextView = findViewById<TextView>(R.id.textView_livesLeft)

        val resumeButton = findViewById<Button>(R.id.button_gameOver_resume)
        val restartButton = findViewById<Button>(R.id.button_gameOver_restart)
        val homeButton = findViewById<Button>(R.id.button_gameOver_home)

        // set the content of the TextViews
        highScoreTextView.text = "$highScore"
        scoreTextView.text = "$score"
        wavesTextView.text = "$waves"
        livesUsedTextView.text = "$livesUsed"
        livesLeftTextView.text = "$livesLeft"

        // if the game is over, hide the RESUME button
        if (isGameOver) {
            resumeButton.visibility = Button.INVISIBLE
        }
        // when the RESUME button is clicked
        resumeButton.setOnClickListener {
            val intent = Intent(this, KotlinInvadersActivity::class.java)
            // TODO: resume the game
        }

        // when the RESTART button is clicked
        restartButton.setOnClickListener {
            val intent = Intent(this, KotlinInvadersActivity::class.java)
            startActivity(intent)
            finish()
        }

        // when the HOME button is clicked
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}