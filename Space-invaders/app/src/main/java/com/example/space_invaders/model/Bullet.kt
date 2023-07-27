package com.example.space_invaders.model

import android.graphics.Bitmap
import android.graphics.RectF

// Base class for Bullet
open class Bullet(
    screenY: Int,
    protected val speed: Float = 350f,
    heightModifier: Float = 20f,
    protected var width: Int = 50,
) {
    val position = RectF()

    // Which way is it shooting
    val up = 0
    val down = 1

    // Going nowhere
    protected var heading = -1

    protected var height = screenY / heightModifier

    var isActive = false

    /**
     * Shoot a bullet from the player
     * @param startX The horizontal coordinate of the start of the bullet
     * @param startY The vertical coordinate of the start of the bullet
     * @param direction The direction of the bullet (up or down)
     * @return true if the bullet was successfully fired
     */
    open fun shoot(startX: Float, startY: Float, direction: Int): Boolean {
        // If bullet is not already active
        if (!isActive) {
            position.left = startX
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

    /**
     * Update the position of the bullet
     * @param fps The frame rate of the game
     */
    open fun update(fps: Long) {
        // Just move the bullet up or down
        if (heading == up) {
            position.top -= speed / fps
        } else {
            position.top += speed / fps
        }

        // Update the bottom position
        position.bottom = position.top + height
    }
}
