package com.example.space_invaders.model

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.BitmapFactory
import com.example.space_invaders.R

/**
 * PlayerShip represents the player's spaceship in the game.
 */
class PlayerShip(
    context: Context,
    private val screenX: Int,
    screenY: Int
) {
    // The player's spaceship bitmap
    var bitmap: Bitmap

    // How wide and high the ship will be
    val width = screenX / 5f
    val height = screenY / 10f

    init {
        // Load the selected spaceship image from shared preferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedImageResId = sharedPreferences.getInt("selectedImageResId", -1)

        // If there is a selected image resource ID, update the bitmap
        if (selectedImageResId != -1) {
            bitmap = BitmapFactory.decodeResource(context.resources, selectedImageResId)
        } else {
            // If no image is selected, use the default spaceship image
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.spaceship1)
        }

        // Stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(), false)
    }



    // This keeps track of where the ship is
    val position = RectF(
        screenX / 2f,
        screenY - height,
        screenX / 2 + width,
        screenY.toFloat()
    )

    // This will hold the pixels per second speed that the ship will move
    private val speed = 450f

    // This data is accessible using ClassName.propertyName
    companion object {
        // Constants to represent the ship's movement direction
        const val stopped = 0
        const val left = 1
        const val right = 2
    }

    // Is the ship moving and in which direction
    // Start off stopped
    var moving = stopped

    /**
     * Update the ship's position based on its movement direction.
     */
    fun update(fps: Long) {
        // Move as long as it doesn't try and leave the screen
        if (moving == left && position.left > 0) {
            position.left -= speed / fps
        } else if (moving == right && position.left < screenX - width) {
            position.left += speed / fps
        }

        position.right = position.left + width
    }
}
