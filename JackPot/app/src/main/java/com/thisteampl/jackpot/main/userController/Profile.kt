package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName

class Profile (
    @SerializedName("name")
    var name : String,
    @SerializedName("region")
    var region : String,
    @SerializedName("job")
    var job : String,
    @SerializedName("stacks")
    var stacks : List<String>,
    @SerializedName("privacy")
    var privacy : Boolean,
    @SerializedName("loginType")
    var loginType : String,
    @SerializedName("career")
    var career : String,
    @SerializedName("auth")
    var auth : String
) {
    override fun toString(): String {
        var stack = ""
        for(i in stacks) {
            stack += "$i, "
        }
        return "Name : " + name + ", Region : " + region + ", Job : " + job + "Stacks : " +
                stack + "Privacy : " + privacy + ", LoginType : " + loginType + ", Career : " +
                career + ", Auth : " + auth
    }
}