package com.example.space_invaders

import android.graphics.RectF

// Class for creating a defensive brick for the shelters
class DefenceBrick(
    row: Int,
    column: Int,
    shelterIndex: Int,
    screenWidth: Int,
    screenHeight: Int) {

    // Flag to determine whether the brick is visible (i.e., not destroyed)
    var isBrickVisible = true

    // Calculate brick width based on screen width
    private val brickWidth = screenWidth / 45

    // Calculate brick height based on screen height
    private val brickHeight = screenHeight / 80

    // Padding to consider for each brick. Set to zero if bullets passing through is not desired
    private val brickPadding = 1

    // Padding to consider for each shelter
    private val shelterPadding = screenWidth / 12f

    // Calculate the starting height for the shelter
    private val shelterStartHeight = screenHeight - screenHeight / 10f * 2f

    // Determine the position of each brick in the shelter
    val brickPosition = RectF(
        // Left
        column * brickWidth + brickPadding +
                shelterPadding * shelterIndex +
                shelterPadding + shelterPadding * shelterIndex,
        // Top
        row * brickHeight + brickPadding + shelterStartHeight,
        // Right
        column * brickWidth + brickWidth - brickPadding +
                shelterPadding * shelterIndex +
                shelterPadding + shelterPadding * shelterIndex,
        // Bottom
        row * brickHeight + brickHeight - brickPadding + shelterStartHeight)
}
