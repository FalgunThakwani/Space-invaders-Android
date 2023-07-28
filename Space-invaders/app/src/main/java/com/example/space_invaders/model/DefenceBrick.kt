package com.example.space_invaders.model

import android.graphics.RectF

/**
 * A single brick
 * @param row The row of the brick
 * @param column The column of the brick
 * @param shelterNumber The shelter number of the brick
 * @param screenX The screen (canvas) width
 * @param screenY The screen (canvas) height
 */
class DefenceBrick(row: Int, column: Int, shelterNumber: Int, screenX: Int, screenY: Int) {

    var isVisible = true

    // How big is each brick
    private val width = screenX / 45
    private val height = screenY / 80

    // Sometimes a bullet slips through this padding.
    // Set padding to zero if this annoys you
    private val brickPadding = 1

    // The number of shelters
    private val shelterPadding = screenX / 12f
    private val startHeight = screenY - screenY / 10f * 2f

    // Now calculate the position of the brick
    // The brick is in the middle of the shelter
    // The shelter number times brick width is the left margin
    // Then add the column times the brick width
    val position = RectF(column * width + brickPadding +
            shelterPadding * shelterNumber +
            shelterPadding + shelterPadding * shelterNumber,
            row * height + brickPadding + startHeight,
            column * width + width - brickPadding +
                    shelterPadding * shelterNumber +
                    shelterPadding + shelterPadding * shelterNumber,
            row * height + height - brickPadding + startHeight)
}