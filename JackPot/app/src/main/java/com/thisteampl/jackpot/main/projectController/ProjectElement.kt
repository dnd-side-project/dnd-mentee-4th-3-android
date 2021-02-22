package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName
import com.thisteampl.jackpot.main.floating.ProjectCreationElement

// get 할 때 사용

class ProjectElement (
    @SerializedName("contents")
    var contents:List<String>,
    @SerializedName("duration")
    var duration:String,
    @SerializedName("interest")
    var interest:String,
    @SerializedName("online")
    var online:String,
    @SerializedName("position")
    var position:List<String>,
    @SerializedName("result/region")
    var region:String,
    @SerializedName("shortDesc")
    var shortDesc:String,
    @SerializedName("stacks")
    var stacks:List<String>,
    @SerializedName("title")
    var title:String
){
    fun getduration():String{
        return duration
    }
}