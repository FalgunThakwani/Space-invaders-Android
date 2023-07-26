package com.example.space_invaders
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
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
        R.drawable.spaceship3,
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
    }

    private fun displayImage(index: Int) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedImageResId = sharedPreferences.getInt("selectedImageResId", -1)
        if (index in 0 until imageList.size) {
            imageView.setImageResource(imageList[index])
            currentIndex = index
            if(selectedImageResId==imageList[index]){
                btnSelect.text = "Selected";
            }else{
                btnSelect.text = "Select";
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

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
