package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName
import com.thisteampl.jackpot.main.projectController.ProjectComponent

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
    @SerializedName("commentPush")
    var commentPush : Boolean,
    @SerializedName("requestAcceptPush")
    var requestAcceptPush : Boolean,
    @SerializedName("requestPush")
    var requestPush : Boolean,
    @SerializedName("stacks")
    var stacks : List<String>,
    @SerializedName("myprojects")
    var myprojects : List<ProjectComponent>,
    @SerializedName("participantProject")
    var participantProject : List<ProjectComponent>,
    @SerializedName("commentProjects")
    var commentProjects : List<ProjectComponent>,
    @SerializedName("participantRequest")
    var participantRequest : List<ProjectComponent>,
    @SerializedName("scrapProjects")
    var scrapProjects : List<ProjectComponent>
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