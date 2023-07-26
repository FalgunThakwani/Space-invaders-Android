package com.example.space_invaders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DockYard : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnLeft: ImageButton
    private lateinit var btnRight: ImageButton
    private lateinit var btnSelect: Button

    private val imageList = listOf(
        R.drawable.playership, // Add more image resource IDs as needed
        R.drawable.spaceship1,
        R.drawable.spaceship2,
        R.drawable.spaceship4,
        R.drawable.spaceship5,
        R.drawable.spaceship6
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dock_yard)

        imageView = findViewById(R.id.imageView)
        btnLeft = findViewById(R.id.btnLeft)
        btnRight = findViewById(R.id.btnRight)
        btnSelect = findViewById(R.id.btnSelect)

        btnLeft.setOnClickListener { navigateImage(-1) }
        btnRight.setOnClickListener { navigateImage(1) }
        btnSelect.setOnClickListener { selectImage() }

        displayImage(currentIndex)

        val highScoreSharedPreferences = getSharedPreferences("Kotlin Invaders", Context.MODE_PRIVATE)
        var highScore =  highScoreSharedPreferences.getInt("highScore", 0)
        val playerBestScoreTextView = findViewById<TextView>(R.id.textView_playerBestScore)
        playerBestScoreTextView.text = "Your Best Score: ${highScore}"

        val backButton: Button = findViewById(R.id.button_dockyard_back)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayImage(index: Int) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedImageResId = sharedPreferences.getInt("selectedImageResId", -1)

        val highScoreSharedPreferences = getSharedPreferences("Kotlin Invaders", Context.MODE_PRIVATE)
        var highScore =  highScoreSharedPreferences.getInt("highScore", 0)

        if (index in 0 until imageList.size) {
            imageView.setImageResource(imageList[index])
            currentIndex = index

            // the first ship is free
            val requiredScore = index * 100

            if(highScore < requiredScore){
                // lock the ship if the score is not enough
                btnSelect.text = "Best Score ${requiredScore} To Unlock"
                btnSelect.isEnabled = false
            }else{
                // score is enough
                if(selectedImageResId==imageList[index]){
                    btnSelect.text = "Selected";
                    btnSelect.isEnabled = false;
                }else{
                    btnSelect.text = "Select";
                    btnSelect.isEnabled = true;
                }
            }

            btnLeft.visibility = if (currentIndex > 0) View.VISIBLE else View.INVISIBLE
            btnRight.visibility = if (currentIndex < imageList.size - 1) View.VISIBLE else View.INVISIBLE
        }
    }
    private fun navigateImage(step: Int) {
        val newIndex = currentIndex + step
        displayImage(newIndex)
    }

    private fun selectImage() {

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedImageResId", imageList[currentIndex])
        editor.apply()
        val message = "Ship ${currentIndex + 1} selected"
        btnSelect.text = "Selected";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        // Navigate to HomeActivity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
