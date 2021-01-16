package com.dnd.dnd3rdproject.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSdk.init(this, "ad84bb483482dd2c398ebc6794b65db9")
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): App {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: App? = null
    }
}