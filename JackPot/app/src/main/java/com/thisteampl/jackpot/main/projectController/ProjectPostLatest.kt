package com.thisteampl.jackpot.main.projectController

data class ProjectPostLatest(
    val duration: List<String>, val interestFilter: List<String>, val pageNumber:Int,
    val pageSize:Int, val regionFilter:String, var sortType:String,
    val stackFilter:List<String>
)