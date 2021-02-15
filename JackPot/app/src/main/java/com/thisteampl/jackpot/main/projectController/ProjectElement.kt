package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName

// get 할 때 사용

class ProjectElement (
    @SerializedName("interest")
    var interest:List<String>,
    @SerializedName("region")
    var region:String,
    @SerializedName("shortDesc")
    var shortDesc:String,
    @SerializedName("stacks")
    var stacks:List<String>,
    @SerializedName("title")
    var title:String

)