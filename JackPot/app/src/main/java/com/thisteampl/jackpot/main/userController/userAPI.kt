package com.thisteampl.jackpot.main.userController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*

interface userAPI {
    @GET("/email/is-exist")
    fun getCheckEmail(@Query("email") email : String) : Call<CheckResponse>

    @POST("/googleLogin")
    fun getCheckGoogleToken(@Body signIn : SNSSignIn) : Call<CheckResponse>

    @POST("/kakaoLogin")
    fun getCheckKakaoToken(@Body signIn : SNSSignIn) : Call<CheckResponse>

    @POST("/naverLogin")
    fun getCheckNaverToken(@Body signIn : SNSSignIn) : Call<CheckResponse>

    @GET("/name/is-exist")
    fun getCheckName(@Query("name") name : String) : Call<CheckResponse>

    @POST("/signin")
    fun getUserLogin(@Body signIn : SignIn) : Call<CheckResponse>

    @POST("/signup")
    fun getUserSignUp(@Body infoDto : User) : Call<CheckResponse>

    @PUT("/password-modify")
    fun getFindPW(@Query("email") email : String, @Query("password") password : String) : Call<CheckResponse>

    @GET("/myprofile")
    fun getProfile() : Call<CheckProfile>

    @PUT("/update-profile")
    fun getUpdateProfile(@Body infoDto : MyProfileEdit) : Call<CheckResponse>

    @PUT("/password-change")
    fun getPasswordChange(@Query("newpassword") newpassword : String, @Query("password") password : String) : Call<CheckResponse>


    @DELETE("/withdraw")
    fun getWithDraw() : Call<CheckResponse>



    companion object {
        fun create() : userAPI? {
            return getBuilder()?.create()
        }
    }

}