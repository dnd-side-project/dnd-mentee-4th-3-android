package com.thisteampl.jackpot.main.projectdetail

data class ProjectDetailComment (
    var projectID: Long,
    var isOwner: Boolean,
    var id: Long,
    var position: String,
    var name: String,
    var watcherName: String,
    var comment: String,
    var date: String,
    var emoticon: String,
    var privacy: Boolean
)