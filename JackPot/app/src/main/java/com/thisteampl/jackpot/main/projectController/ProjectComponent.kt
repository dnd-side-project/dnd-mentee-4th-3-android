package com.thisteampl.jackpot.main.projectController


// /api/filters/projects get All 중 contents 구성요소

class ProjectComponent(
    var id: Long, val interest: String, val position: List<String>,
    val scrapped: Int, val stacks: List<String>, val title: String, val status: String,
    val duration: String, val userIndex: Long, val scrapUsers: Long, val participanting: Long,
    val shortDesc: String, val createdDateTime: String, val region: String, val online: String,
    val participants: List<GetProjectUser>, val comments: List<GetComments>, val requests: List<GetProjectUser>
)

