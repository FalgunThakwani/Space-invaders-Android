package com.example.space_invaders.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.space_invaders.R

// Class for DockYard activity where players can choose their spaceship
class DockYard : AppCompatActivity() {

    // Define UI components
    private lateinit var spaceshipImageView: ImageView
    private lateinit var leftNavigationButton: ImageButton
    private lateinit var rightNavigationButton: ImageButton
    private lateinit var selectSpaceshipButton: Button

    // List of spaceship images
    private val spaceshipImages = listOf(
        R.drawable.spaceship1,
        R.drawable.spaceship2,
        R.drawable.spaceship4,
        R.drawable.spaceship5,
        R.drawable.spaceship6
    )

    // Variable to keep track of the currently displayed image
    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dock_yard)

        // Hide the action bar
        supportActionBar?.hide()

        // Bind UI components
        spaceshipImageView = findViewById(R.id.imageView)
        leftNavigationButton = findViewById(R.id.btnLeft)
        rightNavigationButton = findViewById(R.id.btnRight)
        selectSpaceshipButton = findViewById(R.id.btnSelect)

        // Set onClickListeners for the navigation and select buttons
        leftNavigationButton.setOnClickListener { navigateImage(-1) }
        rightNavigationButton.setOnClickListener { navigateImage(1) }
        selectSpaceshipButton.setOnClickListener { selectImage() }

        // Display the initial image
        displayImage(currentImageIndex)

        // Retrieve and display the player's high score
        val highScoreSharedPreferences = getSharedPreferences("Kotlin Invaders", Context.MODE_PRIVATE)
        val highScore =  highScoreSharedPreferences.getInt("highScore", 0)
        val playerBestScoreTextView = findViewById<TextView>(R.id.textView_playerBestScore)
        playerBestScoreTextView.text = "Your Best Score: ${highScore}"

        // Handle back button click event
        val backButton: Button = findViewById(R.id.button_dockyard_back)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Function to display a specific image from the list based on its index
    private fun displayImage(index: Int) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedImageResId = sharedPreferences.getInt("selectedImageResId", -1)

        val highScoreSharedPreferences = getSharedPreferences("Kotlin Invaders", Context.MODE_PRIVATE)
        val highScore =  highScoreSharedPreferences.getInt("highScore", 0)

        if (index in 0 until spaceshipImages.size) {
            spaceshipImageView.setImageResource(spaceshipImages[index])
            currentImageIndex = index

            // The first spaceship is free
            val requiredScore = index * 100

            // Lock the spaceship if the score is not high enough, otherwise allow it to be selected
            if(highScore < requiredScore){
                selectSpaceshipButton.text = "Best Score ${requiredScore} To Unlock"
                selectSpaceshipButton.isEnabled = false
            } else {
                // If the spaceship is already selected, disable the select button
                if(selectedImageResId==spaceshipImages[index]){
                    selectSpaceshipButton.text = "Selected"
                    selectSpaceshipButton.isEnabled = false
                } else {
                    selectSpaceshipButton.text = "Select"
                    selectSpaceshipButton.isEnabled = true
                }
            }

            // Control the visibility of navigation buttons based on the currently displayed image
            leftNavigationButton.visibility = if (currentImageIndex > 0) View.VISIBLE else View.INVISIBLE
            rightNavigationButton.visibility = if (currentImageIndex < spaceshipImages.size - 1) View.VISIBLE else View.INVISIBLE
        }
    }

    // Function to navigate through the spaceship images
    private fun navigateImage(step: Int) {
        val newIndex = currentImageIndex + step
        displayImage(newIndex)
    }

    // Function to select a spaceship
    private fun selectImage() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedImageResId", spaceshipImages[currentImageIndex])
        editor.apply()
        val message = "Ship ${currentImageIndex + 1} selected"
        selectSpaceshipButton.text = "Selected"
        selectSpaceshipButton.isEnabled = false
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        // Navigate to HomeActivity after spaceship selection
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}