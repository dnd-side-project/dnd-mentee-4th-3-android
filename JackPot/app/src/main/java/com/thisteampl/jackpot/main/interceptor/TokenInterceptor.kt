package com.thisteampl.jackpot.main.interceptor

import android.util.Log
import com.thisteampl.jackpot.common.GlobalApplication.Companion.ACCESS_TOKEN
import com.thisteampl.jackpot.common.GlobalApplication.Companion.sPref
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sPref?.getString(ACCESS_TOKEN, null)
        //Log.e("[Log.e] tag : ", "$jwtToken ")
        if (jwtToken != null) {
            builder.addHeader("x-access-token", jwtToken)
            Log.e("[Log.e] tag : ", "add header")
        }
        return chain.proceed(builder.build())
    }
}