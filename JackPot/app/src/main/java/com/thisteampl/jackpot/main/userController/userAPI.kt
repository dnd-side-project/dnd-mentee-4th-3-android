package com.thisteampl.jackpot.main.userController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.Call
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface userAPI {
    @GET("/email/is-exist")
    fun getCheckEmail(@Query("email") email : String) : Call<CheckResponse>

    @GET("/googleLogin")
    fun getCheckGoogleToken(@Query("googleToken") googleToken : String) : Call<CheckResponse>

    @GET("/kakaoLogin/{kakaoToken}")
    fun getCheckKakaoToken(@Path("kakaoToken") kakaoToken : String) : Call<CheckResponse>

    @GET("/naverLogin")
    fun getCheckNaverToken(@Query("naverToken") naverToken : String) : Call<CheckResponse>

    companion object {
        fun create() : userAPI? {
            return getBuilder()?.create()
        }
    }

}