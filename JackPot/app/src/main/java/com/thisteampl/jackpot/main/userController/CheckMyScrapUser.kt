package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName

class CheckMyScrapUser (
    @SerializedName("message")
    var message : String,
    @SerializedName("result")
    var result : List<UserProfile>
)