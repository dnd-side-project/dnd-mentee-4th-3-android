package com.thisteampl.jackpot.main.projectController


// /api/filters/projects get All 중 contents 구성요소

class PostComment(
    var body: String, val privacy: Boolean, val projectId: Long
)

