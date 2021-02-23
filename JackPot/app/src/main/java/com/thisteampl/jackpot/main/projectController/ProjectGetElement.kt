package com.thisteampl.jackpot.main.projectController

import com.google.gson.annotations.SerializedName


// 프로젝트 이름 바꾸기

class ProjectGetElement(
    @SerializedName("contents")
    var contents:List<ProjectElementMaterial>,
    @SerializedName("pageNumber")
    var pageNumber:Int,
    @SerializedName("pageSize")
    var pageSize:Int,
    @SerializedName("totalPages")
    var totalPages:Int
)