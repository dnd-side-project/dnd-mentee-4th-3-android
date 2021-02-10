package com.thisteampl.jackpot.main.userController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*

interface userAPI {
    @GET("/email/is-exist")
    fun getCheckEmail(@Query("email") email : String) : Call<CheckResponse>

    @GET("/googleLogin")
    fun getCheckGoogleToken(@Query("googleToken") googleToken : String) : Call<CheckResponse>

    @GET("/kakaoLogin/{kakaoToken}")
    fun getCheckKakaoToken(@Path("kakaoToken") kakaoToken : String) : Call<CheckResponse>

    @GET("/naverLogin")
    fun getCheckNaverToken(@Query("naverToken") naverToken : String) : Call<CheckResponse>

    @POST("/signin")
    fun getUserLogin(@Body signIn : SignIn) : Call<CheckResponse>

    @POST("/signup")
    fun getUserSignUp(@Body signUp : SignUp) : Call<SignUp>

    companion object {
        fun create() : userAPI? {
            return getBuilder()?.create()
        }
    }

}