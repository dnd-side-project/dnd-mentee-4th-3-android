package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable

// 참고 자료 : https://hwiyong.tistory.com/19
// Parcelable 직렬화를 위한 interface
data class AttentionProjectList (
    val project_image:Int,
    var attention_project_name:String,
    var attention_recruitment_position:String
)