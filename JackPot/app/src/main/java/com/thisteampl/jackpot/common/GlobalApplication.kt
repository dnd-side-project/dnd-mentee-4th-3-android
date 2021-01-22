package com.thisteampl.jackpot.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

/* 전역 어플리케이션. 카카오 로그인 기능을 할 때 사용한다.
* 이 클래스는 어플리케이션 최상단에서 실행이 된다.*/

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, "ad84bb483482dd2c398ebc6794b65db9")
    }

    companion object {
        lateinit var instance : GlobalApplication
            private set
    }
}