package com.thisteampl.jackpot.main.projectController

import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import com.thisteampl.jackpot.main.floating.ProjectCreationElement
import com.thisteampl.jackpot.main.userController.SignIn
import com.thisteampl.jackpot.main.userController.userAPI
import retrofit2.Call


interface projectAPI {

    companion object{
        fun projectRetrofitService(): projectAPI? = getBuilder()?.create(projectAPI::class.java)
    }


    @POST("/api/projects")
    fun postRecruitmentProject(@Body project: ProjectCreationElement) : Call<ProjectElement>


}