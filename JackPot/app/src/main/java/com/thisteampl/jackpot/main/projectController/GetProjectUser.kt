package com.thisteampl.jackpot.main.projectController

data class GetProjectUser (
    val userIndex : Long,
    val name : String,
    val region : String,
    val position : String,
    val career : String,
    val emoticon : String
)