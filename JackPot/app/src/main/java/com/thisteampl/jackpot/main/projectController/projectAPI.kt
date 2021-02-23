package com.thisteampl.jackpot.main.projectController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import com.thisteampl.jackpot.main.floating.ProjectCreationElement
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*


interface projectAPI {

    companion object{
        fun projectRetrofitService(): projectAPI? = getBuilder()?.create(projectAPI::class.java)
        fun create() : projectAPI? {
            return getBuilder()?.create()
        }
    }


    @POST("/api/projects")
    fun postRecruitmentProject(@Body project: ProjectCreationElement) : Call<ProjectGetElement>


    @GET("/api/projects/get/{id}")
    fun getprojectsID(@Path("id") id:Int) : Call<CheckProject>


    @POST("/api/filters/projects")
    fun getprojectcontents(@Body projectgetlatest:ProjectPostLatest):Call<ProjectGetElement>

    @DELETE("/api/projects/delete/{id}")
    fun getProjectDelete(@Path("id") id: Long) : Call<CheckResponse>

    @POST("/comment")
    fun postComment(@Body commentDto: PostComment) : Call<CheckResponse>

    @POST("/api/projects/change/{id}")
    fun getProjectStatusChange(@Path("id") id: Long, @Query("status") status : String) : Call<CheckResponse>

    @DELETE("/comment/delete/{id}")
    fun deleteComment(@Path("id") id: Long) : Call<CheckResponse>

}