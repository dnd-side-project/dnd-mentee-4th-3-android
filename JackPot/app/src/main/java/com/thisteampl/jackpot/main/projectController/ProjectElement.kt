package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName
import com.thisteampl.jackpot.main.floating.ProjectCreationElement

// get 할 때 사용

class ProjectElement (
    @SerializedName("message")
    var duration:String,
    @SerializedName("result")
    var result:ProjectCreationElement,
    @SerializedName("online")
    var online:String,
    @SerializedName("position")
    var position:List<String>,
    @SerializedName("result/region")
    var region:String,
    @SerializedName("shortDesc")
    var shortDesc:String,
    @SerializedName("result/stacks")
    var stacks:List<String>,
    @SerializedName("title")
    var title:String
){
    fun getduration():String{
        return duration
    }
    fun getinterest():ProjectCreationElement{
        return result
    }
    fun getregion():String{
        return region
    }


}