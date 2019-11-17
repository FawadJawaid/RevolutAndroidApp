package com.androidtest.fawadjawaidmalik.revolut.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.androidtest.fawadjawaidmalik.revolut.R

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is a Splash Activity of the application.
 */
class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Starting the CarListActivity
            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, splashTimeOut)
    }
}