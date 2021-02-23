package com.thisteampl.jackpot.main.projectController

import com.thisteampl.jackpot.common.GlobalApplication.Companion.getBuilder
import com.thisteampl.jackpot.main.floating.ProjectCreationElement
import com.thisteampl.jackpot.main.userController.CheckResponse
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
    fun getProjectContents(@Body projectGetLate:ProjectPostLatest):Call<ProjectGetElement>

    @DELETE("/api/projects/delete/{id}")
    fun getProjectDelete(@Path("id") id: Long) : Call<CheckResponse>

    @POST("/comment")
    fun postComment(@Body commentDto: PostComment) : Call<CheckResponse>

    @POST("/api/projects/change/{id}")
    fun getProjectStatusChange(@Path("id") id: Long, @Query("status") status : String) : Call<CheckResponse>

    @DELETE("/comment/delete/{id}")
    fun deleteComment(@Path("id") id: Long) : Call<CheckResponse>


    @PUT("/api/projects/modify/{id}")
    fun getProjectModify(@Path("id") id: Long, @Body modifyDto:ProjectCreationElement):Call<CheckResponse>


    @POST("/scrap/{projectindex}")
    fun getProjectScrap(@Path("projectindex") id: Long) : Call<CheckResponse>

    @DELETE("/scrap/{projectindex}")
    fun deleteProjectScrap(@Path("projectindex") id: Long) : Call<CheckResponse>

    @POST("/participant/{projectid}")
    fun getProjectParticipant(@Path("projectid") id: Long) : Call<CheckResponse>

    @DELETE("/participant/{projectid}")
    fun deleteProjectParticipant(@Path("projectid") id: Long) : Call<CheckResponse>

    @POST("/participant/accept")
    fun getProjectAccept(@Body accept : ParticipantAccept) : Call<CheckResponse>


}