package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable

// 참고 자료 : https://hwiyong.tistory.com/19
// Parcelable 직렬화를 위한 interface
data class AttentionMemberList(
    val memberimage: Int,
    var attention_member_title: String,
    var attentionmember_recruitment_position: String
)