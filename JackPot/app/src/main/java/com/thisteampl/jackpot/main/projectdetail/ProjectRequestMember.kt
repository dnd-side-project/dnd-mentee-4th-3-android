package com.thisteampl.jackpot.main.projectdetail

data class ProjectRequestMember (
    var projectId: Long,
    var id: Long,
    var position: String,
    var name: String,
    var emoticon: String
)