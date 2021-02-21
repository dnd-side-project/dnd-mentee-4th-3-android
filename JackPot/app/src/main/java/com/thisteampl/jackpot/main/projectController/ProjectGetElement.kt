package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName


class ProjectGetElement(
    @SerializedName("contents")
    var contents:List<ProjectComponent>,
    @SerializedName("pageNumber")
    var pageNumber:Int,
    @SerializedName("pageSize")
    var pageSize:Int,
    @SerializedName("totalPages")
    var totalPages:Int
)