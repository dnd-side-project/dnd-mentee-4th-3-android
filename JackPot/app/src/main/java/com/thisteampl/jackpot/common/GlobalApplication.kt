package com.thisteampl.jackpot.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

/* 전역 어플리케이션. 카카오 로그인 기능을 할 때 사용한다.
* 이 클래스는 어플리케이션 최상단에서 실행이 된다.
* SharedPreferences 구현 : https://blog.yena.io/studynote/2017/12/18/Android-Kotlin-SharedPreferences.html
*
* */

class GlobalApplication : Application() {

    companion object {
        lateinit var tokenPrefs : TokenSharedPreferences
    }

    override fun onCreate() {
        tokenPrefs = TokenSharedPreferences(applicationContext)
        super.onCreate()

        KakaoSdk.init(this, "ad84bb483482dd2c398ebc6794b65db9")
    }

}