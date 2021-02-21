package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
/*
    앱 실행시 로딩 화면을 하는 스플래시 액티비티
    https://bongcando.tistory.com/2
    를 참조하였다.
 */
class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 1000 //1초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}