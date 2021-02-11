package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName

class CheckResponse (
    @SerializedName("token")
    var token : String,
    @SerializedName("errorMessage")
    var errorMessage : String,
    @SerializedName("errorCode")
    var errorCode : String
)