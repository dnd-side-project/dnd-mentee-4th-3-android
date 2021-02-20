package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName

class MyProfile (
    @SerializedName("career")
    var career : String,
    @SerializedName("emoticon")
    var emoticon : String,
    @SerializedName("introduction")
    var introduction : String,
    @SerializedName("name")
    var name : String,
    @SerializedName("loginType")
    var loginType : String,
    @SerializedName("portfolioLink1")
    var portfolioLink1 : String,
    @SerializedName("portfolioLink2")
    var portfolioLink2 : String,
    @SerializedName("position")
    var position : String,
    @SerializedName("privacy")
    var privacy : Boolean,
    @SerializedName("region")
    var region : String,
    @SerializedName("stacks")
    var stacks : List<String>
) {
    override fun toString(): String {
        var stack = ""
        for(i in stacks) {
            stack += "$i, "
        }
        return career + ", " + emoticon + ", " + introduction + ", " + name + ", " + loginType +
                ", " + portfolioLink1 + ", " + portfolioLink2 + ", " + position + ", " + privacy + "" +
                ", " + region + ", " + stack
    }
}