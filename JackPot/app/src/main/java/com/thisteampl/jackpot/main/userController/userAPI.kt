package com.thisteampl.jackpot.main.userController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*

interface userAPI {
    @GET("/email/is-exist")
    fun getCheckEmail(@Query("email") email : String) : Call<CheckResponse>

    @GET("/googleLogin")
    fun getCheckGoogleToken(@Query("token") token : String) : Call<CheckResponse>

    @GET("/kakaoLogin/{kakaoAccessToken}")
    fun getCheckKakaoToken(@Path("kakaoAccessToken") kakaoAccessToken : String) : Call<CheckResponse>

    @GET("/naverLogin")
    fun getCheckNaverToken(@Query("token") token : String) : Call<CheckResponse>

    @GET("/name/is-exist")
    fun getCheckName(@Query("name") name : String) : Call<CheckResponse>

    @POST("/signin")
    fun getUserLogin(@Body signIn : SignIn) : Call<CheckResponse>

    @POST("/signup")
    fun getUserSignUp(@Body signUp : SignUp) : Call<CheckResponse>

    @DELETE("/withdraw")
    fun getWithDraw() : Call<CheckResponse>



    companion object {
        fun create() : userAPI? {
            return getBuilder()?.create()
        }
    }

}