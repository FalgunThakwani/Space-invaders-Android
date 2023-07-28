package com.example.space_invaders.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.SurfaceView
import android.util.Log
import android.view.MotionEvent
import com.example.space_invaders.model.InvaderBullet
import com.example.space_invaders.model.PlayerBullet
import com.example.space_invaders.R
import com.example.space_invaders.model.DefenceBrick
import com.example.space_invaders.model.Invader
import com.example.space_invaders.model.PlayerShip
import com.example.space_invaders.view.GameOverActivity


class InvadersView(context: Context,
                   private val size: Point)
    : SurfaceView(context),
        Runnable {

    // For making a noise
    private val soundPlayer = SoundPlayer(context)

    // This is our thread
    private val gameThread = Thread(this)

    // A boolean which we will set and unset
    private var playing = false

    // Game is paused at the start
    private var paused = true

    // A Canvas and a Paint object
    private var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()

    // The players ship
    private var playerShip: PlayerShip = PlayerShip(context, size.x, size.y)

    // Some Invaders
    private val invaders = ArrayList<Invader>()
    private var numInvaders = 0

    // The player's shelters are built from bricks
    private val bricks = ArrayList<DefenceBrick>()
    private var numBricks: Int = 0

    // The player's playerBullet
    // much faster and half the length
    // compared to invader's bullet
    private val bulletFrame1 = BitmapFactory.decodeResource(context.resources, R.drawable.bullet_frame1_reduced)

    private var playerBullet = PlayerBullet(size.y, 1000f, 40f,bulletFrame1)

    // The invaders bullets
    private val invadersInvaderBullets = ArrayList<InvaderBullet>()
    private var nextBullet = 0
    private val maxInvaderBullets = 10

    // The score
    private var score = 0

    // The wave number
    private var waves = 1

    // Lives
    private var lives = 3

    // To remember the high score
    private val prefs: SharedPreferences = context.getSharedPreferences(
            "Kotlin Invaders",
            Context.MODE_PRIVATE)

    private var highScore =  prefs.getInt("highScore", 0)

    // How menacing should the sound be?
    private var menaceInterval: Long = 1000

    // Which menace sound should play next
    private var uhOrOh: Boolean = false
    // When did we last play a menacing sound
    private var lastMenaceTime = System.currentTimeMillis()


    // Get the preference for haptic feedback from user
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Get user's preference for sound
    val soundEnabled = sharedPreferences.getBoolean("SOUND_ENABLED", true)

    private fun prepareLevel() {
        // Here we will initialize the game objects
        // Build an army of invaders
        Invader.numberOfInvaders = 0
        numInvaders = 0

        for (column in 0..6) {
            for (row in 0..3) {
                invaders.add(Invader(context,
                    row,
                    column,
                    size.x,
                    size.y))

                numInvaders++
            }
        }

        // Build the shelters
        numBricks = 0

        for (shelterNumber in 0..4) {
            for (column in 0..5) {
                for (row in 0..4) {
                    bricks.add(DefenceBrick(row,
                        column,
                        shelterNumber,
                        size.x,
                        size.y))

                    numBricks++
                }
            }
        }

        // Initialize the invadersBullets array
        for (i in 0 until maxInvaderBullets) {
            invadersInvaderBullets.add(InvaderBullet(size.y))
        }
    }

    override fun run() {
        // This variable tracks the game frame rate
        var fps: Long = 0

        while (playing) {

            // Capture the current time
            val startFrameTime = System.currentTimeMillis()

            // Update the frame
            if (!paused) {
                update(fps)
            }

            // Draw the frame
            draw()

            // Calculate the fps rate this frame
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }

            // Play a sound based on the menace level
            if (!paused && ((startFrameTime - lastMenaceTime) > menaceInterval))
                menacePlayer()
        }
        gameOver(true)
    }

    private fun menacePlayer() {
        if (soundEnabled) {
            if (uhOrOh) {
                // Play Uh
                soundPlayer.playSound(SoundPlayer.uhID)
            } else {
                // Play Oh
                soundPlayer.playSound(SoundPlayer.ohID)
            }
        }

        // Reset the last menace time
        lastMenaceTime = System.currentTimeMillis()
        // Alter value of uhOrOh
        uhOrOh = !uhOrOh

    }

    val explosionBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.explosion) // Explosion bitmap

    private fun update(fps: Long) {
        // Update the state of all the game objects

        // Move the player's ship
        playerShip.update(fps)

        // Did an invader bump into the side of the screen
        var bumped = false

        // Has the player lost
        var lost = false

        // Update all the invaders if visible
        for (invader in invaders) {

            if (invader.isVisible) {
                // Move the next invader
                invader.update(fps)

                // Does he want to take a shot?
                if (invader.takeAim(playerShip.position.left,
                                playerShip.width,
                                waves)) {

                    // If so try and spawn a bullet
                    if (invadersInvaderBullets[nextBullet].shoot(invader.position.left
                                    + invader.width / 2,
                                    invader.position.top, playerBullet.down)) {

                        // Shot fired
                        // Prepare for the next shot
                        nextBullet++

                        // Loop back to the first one if we have reached the last
                        if (nextBullet == maxInvaderBullets) {
                            // This stops the firing of bullet
                            // until one completes its journey
                            // Because if bullet 0 is still active
                            // shoot returns false.
                            nextBullet = 0
                        }
                    }
                }

                // If that move caused them to bump
                // the screen change bumped to true
                if (invader.position.left > size.x - invader.width
                        || invader.position.left < 0) {

                    bumped = true

                }
            }
        }

        // Update the players playerBullet
        if (playerBullet.isActive) {
            playerBullet.update(fps)
        }

        // Update all the invaders bullets if active

        for (bullet in invadersInvaderBullets) {
            if (bullet.isActive) {
                bullet.update(fps)
            }
        }

        // Did an invader bump into the edge of the screen
        if (bumped) {

            // Move all the invaders down and change direction
            for (invader in invaders) {
                invader.dropDownAndReverse(waves)
                // Have the invaders landed
                if (invader.position.bottom >= size.y && invader.isVisible) {
                    lost = true
                }
            }
        }

        // Has the player's playerBullet hit the top of the screen
        if (playerBullet.position.bottom < 0) {
            playerBullet.isActive =false
        }

        // Has an invaders playerBullet hit the bottom of the screen
        for (bullet in invadersInvaderBullets) {
            if (bullet.position.top > size.y) {
                bullet.isActive = false
            }
        }

        // Has the player's playerBullet hit an invader
        if (playerBullet.isActive) {
            for (invader in invaders) {
                if (invader.isVisible) {
                    if (RectF.intersects(playerBullet.position, invader.position)) {
                        invader.isVisible = false

                        // Get shared preferences
                        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                        // Check if haptic feedback is enabled
                        if (sharedPreferences.getBoolean("HAPTIC_FEEDBACK_INVADER", true)) {
                            // Vibrate the device
                            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                // Deprecated in API 26
                                vibrator.vibrate(500)
                            }
                        }
                        // Play sound if enabled
                        if (soundEnabled) {
                            soundPlayer.playSound(SoundPlayer.invaderExplodeID)
                        }

                        playerBullet.isActive = false
                        Invader.numberOfInvaders--
                        score += 10
                        if (score > highScore) {
                            highScore = score
                        }

                        // Has the player cleared the level
                        if (Invader.numberOfInvaders == 0) {
                            paused = true
                            lives++
                            invaders.clear()
                            bricks.clear()
                            invadersInvaderBullets.clear()
                            prepareLevel()
                            waves++
                            break
                        }

                        // Don't check any more invaders
                        break
                    }
                }
            }
        }

        // Has an alien playerBullet hit a shelter brick
        for (bullet in invadersInvaderBullets) {
            if (bullet.isActive) {
                for (brick in bricks) {
                    if (brick.isVisible) {
                        if (RectF.intersects(bullet.position, brick.position)) {
                            // A collision has occurred
                            bullet.isActive = false
                            brick.isVisible = false

                            // Get shared preferences
                            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                            // Play sound if enabled
                            if (soundEnabled) {
                                soundPlayer.playSound(SoundPlayer.damageShelterID)
                            }
                        }
                    }
                }
            }
        }

        // Has a player playerBullet hit a shelter brick
        if (playerBullet.isActive) {
            for (brick in bricks) {
                if (brick.isVisible) {
                    if (RectF.intersects(playerBullet.position, brick.position)) {
                        // A collision has occurred
                        playerBullet.isActive = false
                        brick.isVisible = false

                        // Get shared preferences
                        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                        // Play sound if enabled
                        if (soundEnabled) {
                            soundPlayer.playSound(SoundPlayer.damageShelterID)
                        }
                    }
                }
            }
        }

        // Has an invader playerBullet hit the player ship
        for (bullet in invadersInvaderBullets) {
            if (bullet.isActive) {
                if (RectF.intersects(playerShip.position, bullet.position)) {
                    bullet.isActive = false
                    lives --

                    // Get the explosion image and scale it to the player ship size
                    val explosionBitmap = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.explosion)
                    val scaledExplosionBitmap = Bitmap.createScaledBitmap(
                        explosionBitmap,
                        playerShip.width.toInt(),
                        playerShip.height.toInt(),
                        false)

                    // Change the player ship's image to the scaled explosion
                    playerShip.bitmap = scaledExplosionBitmap


                    // Create a Handler to revert back to the selected image after 2 seconds
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        // Get the selected image resource ID from SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val selectedImageResId = sharedPreferences.getInt("selectedImageResId", -1)

                        // Load the appropriate bitmap based on the selectedImageResId
                        val selectedImageBitmap = if (selectedImageResId != -1) {
                            BitmapFactory.decodeResource(
                                context.resources,
                                selectedImageResId
                            )
                        } else {
                            BitmapFactory.decodeResource(
                                context.resources,
                                R.drawable.spaceship1
                            )
                        }

                        // Scale the selected image bitmap to the player ship size
                        val scaledSelectedBitmap = Bitmap.createScaledBitmap(
                            selectedImageBitmap,
                            playerShip.width.toInt(),
                            playerShip.height.toInt(),
                            false
                        )

                        // Set the player ship's bitmap to the scaled selected image
                        playerShip.bitmap = scaledSelectedBitmap
                    }, 2000) // Delay of 2 seconds (2000 milliseconds)

                    // get shared preferences
                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                    // check if haptic feedback is enabled
                    if (sharedPreferences.getBoolean("HAPTIC_FEEDBACK_PLAYER", true)) {
                        // vibrate the device
                        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            //deprecated in API 26
                            vibrator.vibrate(500)
                        }
                    }
                    // Play sound if enabled
                    if (soundEnabled) {
                        soundPlayer.playSound(SoundPlayer.playerExplodeID)
                    }

                    // Is it game over?
                    if (lives == 0) {
                        lost = true
                        break
                    }
                }
            }
        }

        if (lost) {
            paused = true
            playing = false
/*            lives = 3
            score = 0
            waves = 1*/
            invaders.clear()
            bricks.clear()
            invadersInvaderBullets.clear()
            prepareLevel()
        }
    }

    private fun draw() {
        // Make sure our drawing surface is valid or the game will crash
        if (holder.surface.isValid) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawColor(Color.argb(255, 0, 0, 0))

            // draw the exit button
            var tempSettingBitmap: Bitmap
            tempSettingBitmap = BitmapFactory.decodeResource(context.resources,R.drawable.exit)
            canvas.drawBitmap(Bitmap.createScaledBitmap(tempSettingBitmap,size.x.toInt()/11, size.y.toInt()/22 , false), size.x - 120f , 0f,paint)


            // Choose the brush color for drawing
            paint.color = Color.argb(255, 0, 255, 0)

            // Draw all the game objects here
            // Now draw the player spaceship
            canvas.drawBitmap(playerShip.bitmap, playerShip.position.left,
                    playerShip.position.top
                    , paint)

            // Draw the invaders
            for (invader in invaders) {
                if (invader.isVisible) {
                    if (uhOrOh) {
                        canvas.drawBitmap(Invader.bitmap1,
                                invader.position.left,
                                invader.position.top,
                                paint)
                    } else {
                        canvas.drawBitmap(Invader.bitmap2,
                                invader.position.left,
                                invader.position.top,
                                paint)
                    }
                }
            }

            // Draw the bricks if visible
            for (brick in bricks) {
                if (brick.isVisible) {
                    canvas.drawRect(brick.position, paint)
                }
            }

            // Draw the players playerBullet if active
            if (playerBullet.isActive) {
                val currentFrame = playerBullet.getCurrentFrame()
                canvas.drawBitmap(currentFrame, playerBullet.position.left, playerBullet.position.top, paint)
            }

            // Draw the invaders bullets
            for (bullet in invadersInvaderBullets) {
                if (bullet.isActive) {
                    canvas.drawRect(bullet.position, paint)
                }
            }

            // Draw the score and remaining lives
            // Change the brush color
            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 50f
            canvas.drawText("Score: $score  |  Lives: $lives  |  Wave: " +
                    "$waves  |  Best: $highScore", 20f, 75f, paint)

            // Draw everything to the screen
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // If SpaceInvadersActivity is paused/stopped
    // then shut down our thread.
    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }

        val prefs = context.getSharedPreferences(
                "Kotlin Invaders",
                Context.MODE_PRIVATE)

        val oldHighScore = prefs.getInt("highScore", 0)

        if(highScore > oldHighScore) {
            val editor = prefs.edit()

            editor.putInt(
                    "highScore", highScore)

            editor.apply()
        }
    }

    // If SpaceInvadersActivity is started then
    // start our thread.
    fun resume() {
        playing = true
        prepareLevel()
        gameThread.start()
    }

    /**
     * This method is calleed whether the game is over or paused
     * @param isGameOver is true if the game is over
     */
    private fun gameOver(isGameOver : Boolean) {
        val intent = Intent(this.context, GameOverActivity::class.java)

        // pass score & waves & highScore
        intent.putExtra("highScore", highScore)
        intent.putExtra("score", score)
        intent.putExtra("waves", waves)
        intent.putExtra("livesLeft", lives)
        intent.putExtra("livesUsed", 3 - lives)
        intent.putExtra("isGameOver", isGameOver)

        context.startActivity(intent)
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.

    /**
     * This method is called when the player touches the screen
     * @param motionEvent The motion event that was detected
     * @return true if the event was handled
     */
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        // Get the general position of the touch
        val gamePlayAreaThreshold = size.y - (size.y / 8)
        val settingsAreaThreshold = size.y / 8

        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            // Player has touched the screen
            // Or moved their finger while touching screen
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> handleMotionEvent(motionEvent, gamePlayAreaThreshold, settingsAreaThreshold)

            // Player has removed finger from screen
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_UP -> handleMotionEventUp(motionEvent, gamePlayAreaThreshold)
        }
        return true
    }

    /**
     * This method handles the motion event when the player touches the screen
     */
    private fun handleMotionEvent(
        motionEvent: MotionEvent,
        gamePlayAreaThreshold: Int,
        settingsAreaThreshold: Int
    ) {
        paused = false

        when {
            // Player has touched the game play area to move the ship
            motionEvent.y > gamePlayAreaThreshold -> handleGamePlayAreaMotion(motionEvent)
            // Player has touched the settings area to exit the game
            motionEvent.y < settingsAreaThreshold -> handleSettingsAreaMotion(motionEvent)
            // Player has touched the game play area to shoot
            else -> handleShootingMotion()
        }
    }

    /**
     * This method handles the motion event when the player removes their finger from the screen
     * @param motionEvent The motion event that was detected
     * @param gamePlayAreaThreshold The threshold for the game play area
     */
    private fun handleMotionEventUp(motionEvent: MotionEvent, gamePlayAreaThreshold: Int) {
        if (motionEvent.y > gamePlayAreaThreshold) {
            playerShip.moving = PlayerShip.stopped
        }
    }

    /**
     * This method handles the motion event when the player touches the game play area
     * @param motionEvent The motion event that was detected
     */
    private fun handleGamePlayAreaMotion(motionEvent: MotionEvent) {
        playerShip.moving = if (motionEvent.x > size.x / 2) PlayerShip.right else PlayerShip.left
    }

    /**
     * This method handles the motion event when the player touches the settings area
     * @param motionEvent The motion event that was detected
     */
    private fun handleSettingsAreaMotion(motionEvent: MotionEvent) {
        if (motionEvent.x > size.x - 100) {
            // abort the ship
            gameOver(false)
        }
    }

    /**
     * This method handles the motion event when the player touches the game play area to shoot
     * @param motionEvent The motion event that was detected
     */
    private fun handleShootingMotion() {
        // Shots fired
        if (playerBullet.shoot(
                playerShip.position.left + playerShip.width / 2f,
                playerShip.position.top,
                playerBullet.up
            )
        ) {
            // Play sound if enabled
            if (soundEnabled) {
                soundPlayer.playSound(SoundPlayer.damageShelterID)
            }
        }
    }
}
