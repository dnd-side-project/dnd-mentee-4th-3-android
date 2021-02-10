package com.thisteampl.jackpot.main.interceptor

import android.util.Log
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String = prefs.getString("token", "NO_TOKEN")
        //Log.e("[Log.e] tag : ", "$jwtToken ")
        if (jwtToken != "NO_TOKEN") {
            builder.addHeader("Authorization", "Bearer $jwtToken")
            Log.e("[Log.e] tag : ", "add header : $jwtToken")
        }
        return chain.proceed(builder.build())
    }
}