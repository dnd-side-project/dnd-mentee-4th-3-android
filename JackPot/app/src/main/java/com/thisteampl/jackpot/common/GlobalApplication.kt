package com.thisteampl.jackpot.common

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.thisteampl.jackpot.main.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/* 전역 어플리케이션. 카카오 로그인 기능을 할 때 사용한다.
* 이 클래스는 어플리케이션 최상단에서 실행이 된다.
* SharedPreferences 구현 : https://blog.yena.io/studynote/2017/12/18/Android-Kotlin-SharedPreferences.html
*
* */

class GlobalApplication : Application() {

    companion object {
        var BASE_URL = "http://52.78.49.189:8080"

        var ACCESS_TOKEN = "ACCESS_TOKEN"
        var sPref : SharedPreferences? = null

        var retrofit: Retrofit? = null
        fun getBuilder(): Retrofit? {
            if (retrofit == null) {
                val client: OkHttpClient = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(TokenInterceptor()) // JWT 자동 헤더 전송
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

    override fun onCreate() {
        super.onCreate()

        if(sPref == null) {
            sPref = applicationContext.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE)
        }

        KakaoSdk.init(this, "ad84bb483482dd2c398ebc6794b65db9")
    }

}