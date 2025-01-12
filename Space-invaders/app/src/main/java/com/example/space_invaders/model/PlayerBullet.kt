package com.example.space_invaders.model

import android.graphics.Bitmap
import com.example.space_invaders.model.Bullet

/**
 * Class for PlayerBullet
 * @param screenY The vertical resolution of the screen
 * @param speed The speed of the bullet
 * @param heightModifier The height modifier of the bullet
 * @param bulletBitmap The bitmap of the bullet
 * @constructor Create empty Player bullet
 */
class PlayerBullet(
    screenY: Int,
    speed: Float = 350f,
    heightModifier: Float = 20f,
    private var bulletBitmap: Bitmap
) : Bullet(screenY, speed, heightModifier) {
    private var frameTime = 0L
    private val frameDuration = 100

    /**
     * Update the position of the bullet
     * @param fps The frame rate of the game
     */
    override fun update(fps: Long) {
        // Check if the bullet animation should move to the next frame
        val currentTime = System.currentTimeMillis()
        if (currentTime > frameTime + frameDuration) {
            frameTime = currentTime
        }

        // Call the update method of the super class (Bullet)
        super.update(fps)
    }

    /**
     * Get the current frame of the bullet
     * @return The current frame of the bullet
     */
    fun getCurrentFrame(): Bitmap {
        return bulletBitmap
    }
}