package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName

data class CheckProject (
    @SerializedName("message")
    var message:String,
    @SerializedName("result")
    var result : ProjectComponent
)