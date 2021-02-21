package com.thisteampl.jackpot.main.projectController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import retrofit2.http.Body
import retrofit2.http.POST
import com.thisteampl.jackpot.main.floating.ProjectCreationElement
import com.thisteampl.jackpot.main.userController.userAPI
import retrofit2.Call
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path


interface projectAPI {

    companion object{
        fun projectRetrofitService(): projectAPI? = getBuilder()?.create(projectAPI::class.java)
        fun create() : projectAPI? {
            return getBuilder()?.create()
        }
    }


    @POST("/api/projects")
    fun postRecruitmentProject(@Body project: ProjectCreationElement) : Call<ProjectElement>

    @GET("/api/projects/get/{id}")
    fun getprojectsID(@Path("id") id:Int) : Call<ProjectElement>

    @POST("/api/filters/projects")
    fun getprojectcontents(@Body projectgetlatest:ProjectPostLatest):Call<ProjectGetElement>

}