package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable

// 참고 자료 : https://hwiyong.tistory.com/19
// Parcelable 직렬화를 위한 interface
class AttentionProjectList (val project_image:Int,var attention_project_name:String?, var attention_recruitment_position:String?,var update_date:String?,
                     var stack1:String?,var stack2:String?,var stack3:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(project_image)
        parcel.writeString(attention_project_name)
        parcel.writeString(attention_recruitment_position)
        parcel.writeString(update_date)
        parcel.writeString(stack1)
        parcel.writeString(stack2)
        parcel.writeString(stack3)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttentionProjectList> {
        override fun createFromParcel(parcel: Parcel): AttentionProjectList {
            return AttentionProjectList(parcel)
        }

        override fun newArray(size: Int): Array<AttentionProjectList?> {
            return arrayOfNulls(size)
        }
    }
}