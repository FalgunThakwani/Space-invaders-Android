package com.example.space_invaders

import android.app.Activity
import android.graphics.Point
import android.os.Bundle

class InvadersActivity : Activity() {

    private lateinit var invadersView: InvadersView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the resolution into a Point object
        val size = Point()
        windowManager.defaultDisplay.getSize(size)

        // Initialize gameView and set it as the view
        invadersView = InvadersView(this, size)
        setContentView(invadersView)
    }

    override fun onResume() {
        super.onResume()
        invadersView.resume()
    }

    override fun onPause() {
        super.onPause()
        invadersView.pause()
    }
}