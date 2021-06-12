package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.view.AuthorizationActivity


class SplashActivity : AppCompatActivity() {
    private var TIME_OUT:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        loadSplashScreen()
    }
    private fun loadSplashScreen(){

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }
}