package com.thisteampl.jackpot.main.userController

import com.google.gson.annotations.SerializedName
import com.thisteampl.jackpot.main.projectController.ProjectComponent

class UserRelatedFilteringGet(
    @SerializedName("contents")
    var contents:List<UserRelatedFilteringcontents>,
    @SerializedName("pageNumber")
    var pageNumber:Int,
    @SerializedName("pageSize")
    var pageSize:Int,
    @SerializedName("totalPages")
    var totalPages:Int
)