package com.thisteampl.jackpot.main.projectController


// /api/filters/projects get All 중 contents 구성요소

class ProjectComponent(
    var id: Long, val interest: String, val position: List<String>,
    val scrapped: Int, val stacks: List<String>, val title: String
)

