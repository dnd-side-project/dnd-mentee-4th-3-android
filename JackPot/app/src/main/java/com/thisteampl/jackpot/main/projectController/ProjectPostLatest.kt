package com.thisteampl.jackpot.main.projectController

class ProjectPostLatest(
    val duration: List<String>, val interestFilter: List<String>, val pageNumber:Long,
    val pageSize:Long, val regionFilter:String, val sortType:String,
    val stackFilter:List<String>
)