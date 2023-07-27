package com.example.space_invaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.BitmapFactory
import java.util.*

class Invader(context: Context, row: Int, column: Int, screenX: Int, screenY: Int) {
    // How wide, high and spaced out are the invader will be
    var width = screenX / 17f
    private var height = screenY / 35f
    private val padding = screenX / 25

    var position = RectF(
        column * (width + padding),
        100 + row * (width + padding),
        column * (width + padding) + width,
        100 + row * (width + padding) + height
    )

    // Pixels per second speed that the invader will move
    private var speed = 40f
    private val left = 1
    private val right = 2

    // Is the ship moving and in which direction
    private var shipMoving = right
    var isVisible = true

    companion object {
        // The alien ship will be represented by a Bitmap
        lateinit var bitmap1: Bitmap
        lateinit var bitmap2: Bitmap

        // Keep track of the number of instances that are active
        var numberOfInvaders = 0
    }

    init {
        // Initialize the bitmaps
        bitmap1 = BitmapFactory.decodeResource(context.resources, R.drawable.invader1)
        bitmap2 = BitmapFactory.decodeResource(context.resources, R.drawable.invader2)

        // Stretch the first bitmap to a size appropriate for the screen resolution
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, width.toInt(), height.toInt(), false)

        // Stretch the second bitmap as well
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, width.toInt(), height.toInt(), false)
        numberOfInvaders++
    }

    fun update(fps: Long) {
        when (shipMoving) {
            left -> position.left -= speed / fps
            right -> position.left += speed / fps
        }
        position.right = position.left + width
    }

    fun dropDownAndReverse() {
        shipMoving = if (shipMoving == left) right else left
        position.apply {
            top += height
            bottom += height
        }
    }

    fun takeAim(playerShipX: Float, playerShipLength: Float, waves: Int): Boolean {
        val generator = Random()
        // If near the player consider taking a shot
        if ((playerShipX + playerShipLength > position.left && playerShipX + playerShipLength < position.left + width) ||
            (playerShipX > position.left && playerShipX < position.left + width)) {
            // The fewer invaders the more each invader shoots
            val randomNumber = generator.nextInt((100 * numberOfInvaders) / waves)
            if (randomNumber == 0) {
                return true
            }
        }
        // If firing randomly (not near the player)
        val randomNumber = generator.nextInt(150 * numberOfInvaders)
        return randomNumber == 0
    }
}