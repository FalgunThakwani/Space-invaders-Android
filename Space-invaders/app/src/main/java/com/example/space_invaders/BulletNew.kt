package com.example.space_invaders

import android.graphics.Bitmap
import android.graphics.RectF

class BulletNew(screenY: Int,
                private val speed: Float = 350f,
                heightModifier: Float = 20f,
                private val bulletBitmap: Bitmap // Single bitmap for bullet animation
) {

    val position = RectF()

    // Animation frame properties
    private var frameTime = 0L
    private val frameDuration = 100

    // Which way is it shooting
    val up = 0
    val down = 1

    // Going nowhere
    private var heading = -1

    private val width = 2
    private var height = screenY / heightModifier

    var isActive = false

    fun shoot(startX: Float, startY: Float, direction: Int): Boolean {
        if (!isActive) {
            position.left = startX - 50
            position.top = startY
            position.right = position.left + width
            position.bottom = position.top + height
            heading = direction
            isActive = true
            return true
        }

        // Bullet already active
        return false
    }

    fun update(fps: Long) {
        // Check if the bullet animation should move to the next frame
        val currentTime = System.currentTimeMillis()
        if (currentTime > frameTime + frameDuration) {
            frameTime = currentTime
        }

        // Move the bullet based on the current heading and speed
        if (heading == up) {
            position.top -= speed / fps
        } else {
            position.top += speed / fps
        }
        // Update the bottom position
        position.bottom = position.top + height
    }

    fun getCurrentFrame(): Bitmap {
        // Return the single bitmap for bullet animation
        return bulletBitmap
    }
}
