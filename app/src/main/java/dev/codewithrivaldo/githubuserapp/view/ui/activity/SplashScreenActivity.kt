package dev.codewithrivaldo.githubuserapp.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import dev.codewithrivaldo.githubuserapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            val mIntent = Intent(this, ContainerActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 4000)
    }
}