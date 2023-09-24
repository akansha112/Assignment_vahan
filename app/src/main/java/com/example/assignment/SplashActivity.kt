package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class SplashActivity : AppCompatActivity() {

    private lateinit var splashImage: ImageView
    private val SPLASH_TIME_OUT:Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashImage = findViewById(R.id.splash_image)
          //Hide the navigation bar
        val controller=WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.statusBars())

        // Load the rotation animation
        val rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_anim)

        // Apply the animation to the splashImage
        splashImage.startAnimation(rotationAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }
}
