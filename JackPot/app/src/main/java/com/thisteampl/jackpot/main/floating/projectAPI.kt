package com.thisteampl.jackpot.main.floating

import com.thisteampl.jackpot.main.userController.userAPI
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.SignIn
import retrofit2.Call

interface projectAPI {

    companion object {
        fun create(): projectAPI? {
            // GlobalApplication getBuilder 메소드 호출
            return getBuilder()?.create()
        }
    }

    @POST("/api/projects")
    fun getRecruitmentProject(@Body project: ProjectCreationElement) : Call<CheckResponse>


}