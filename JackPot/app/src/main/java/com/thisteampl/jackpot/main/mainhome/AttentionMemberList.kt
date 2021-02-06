package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable

// 참고 자료 : https://hwiyong.tistory.com/19
// Parcelable 직렬화를 위한 interface
class AttentionMemberList (val memberiamge: Int,var attention_member_name:String, var attentionmember_recruitment_position:String,
                           var update_date:String,var stack1:String,var stack2:String,var stack3:String)