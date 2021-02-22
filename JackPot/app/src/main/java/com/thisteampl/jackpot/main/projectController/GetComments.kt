package com.thisteampl.jackpot.main.projectController

data class GetComments (
    val id : Long,
    val body : String,
    val date : String,
    val authorName : String,
    val authorPosition : String,
    val emoticon : String,
    val privacy : Boolean
)