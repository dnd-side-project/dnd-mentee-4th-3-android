package com.thisteampl.jackpot.main.projectdetail

data class ProjectDetailComment (
    var projectOwnerName: String,
    var id: Long,
    var position: String,
    var name: String,
    var comment: String,
    var date: String,
    var emoticon: String,
    var privacy: Boolean
)