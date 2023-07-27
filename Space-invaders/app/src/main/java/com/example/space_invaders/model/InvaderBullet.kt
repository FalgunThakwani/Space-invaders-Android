package com.example.space_invaders.model

import com.example.space_invaders.model.Bullet

/**
 * Class for InvaderBullet
 * @param screenY The vertical resolution of the screen
 * @param speed The speed of the bullet
 * @param heightModifier The height modifier of the bullet
 */
class InvaderBullet(screenY: Int, speed: Float = 350f, heightModifier: Float = 20f)
    : Bullet(screenY, speed, heightModifier, width = 2)