package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable



data class RecentlyRegisterList(
    val recentlyiamge: Int,
    var recentlyregister_project_name: String?, var recentlyregister_recruitment_position: String?,
    var update_date: String?,
    var technology_stack:List<String>?
)

