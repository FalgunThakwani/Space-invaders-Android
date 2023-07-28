package com.example.space_invaders.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import java.util.*
import android.graphics.BitmapFactory
import com.example.space_invaders.R

/**
 * An invader
 * @param context The context
 * @param row The row of the invader
 * @param column The column of the invader
 * @param screenX The screen (canvas) width
 * @param screenY The screen (canvas) height
 * @constructor Create empty Invader
 */
class Invader(context: Context, row: Int, column: Int, screenX: Int, screenY: Int) {
    // How wide, high and spaced out are the invader will be
    var width = screenX / 17f

    private var height = screenY / 35f
    private val padding = screenX / 25

    var position = RectF(
            column * (width + padding),
        100 + row * (width + padding),
            column * (width + padding) + width,
            100 + row * (width + padding / 4) + height
    )

    // This will hold the pixels per second speed that the invader will move
    private var speed = 40f

    private val left = 1
    private val right = 2

    // Is the ship moving and in which direction
    private var shipMoving = right

    var isVisible = true

    /**
     * bitmap1 and bitmap2 will hold the two images for the invader
     * This will alternate every time an invader is created
     */
    companion object {
        // The alien ship will be represented by a Bitmap
        lateinit var bitmap1: Bitmap
        lateinit var bitmap2: Bitmap

        // keep track of the number of instances
        // that are active
        var numberOfInvaders = 0
    }

    /**
     * Initialize the bitmaps
     */
    init {
        // Initialize the bitmaps
        bitmap1 = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.invader1)

        bitmap2 = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.invader2)

        // stretch the first bitmap to a size
        // appropriate for the screen resolution
        bitmap1 = Bitmap.createScaledBitmap(
                bitmap1,
                (width.toInt()),
                (height.toInt()),
                false)

        // stretch the second bitmap as well
        bitmap2 = Bitmap.createScaledBitmap(
                bitmap2,
                (width.toInt()),
                (height.toInt()),
                false)

        numberOfInvaders ++
    }

    /**
     * Update the invaders position
     * @param fps
     */
    fun update(fps: Long) {
        if (shipMoving == left) {
            position.left -= speed / fps
        }
        if (shipMoving == right) {
            position.left += speed / fps
        }
        position.right = position.left + width
    }

    /**
     * Drop the ship down the screen and reverse the direction
     */
    fun dropDownAndReverse(waveNumber: Int) {
        shipMoving = if (shipMoving == left) {
            right
        } else {
            left
        }
        position.top += height
        position.bottom += height
    }

    /**
     * Aim a shot at the player
     * @param playerShipX The player's ship horizontal coordinate
     * @param playerShipLength The player's ship length
     * @param waves The current wave
     * @return True if the player's ship is hit
     */
    fun takeAim(playerShipX: Float, playerShipLength: Float, waves: Int) : Boolean {

        val generator = Random()
        var randomNumber: Int

        // If near the player consider taking a shot
        if (playerShipX + playerShipLength > position.left &&
                playerShipX + playerShipLength < position.left + width ||
                playerShipX > position.left && playerShipX < position.left + width) {

            // The fewer invaders the more each invader shoots
            // The higher the wave the more the invader shoots
            randomNumber = generator.nextInt((100 * numberOfInvaders) / waves)
            if (randomNumber == 0) {
                return true
            }
        }

        // If firing randomly (not near the player)
        randomNumber = generator.nextInt(150 * numberOfInvaders)
        return randomNumber == 0
    }
}
