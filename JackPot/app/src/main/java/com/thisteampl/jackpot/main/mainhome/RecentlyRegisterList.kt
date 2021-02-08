package com.thisteampl.jackpot.main.mainhome

import android.os.Parcel
import android.os.Parcelable


// 참고 자료 : https://hwiyong.tistory.com/19
// Parcelable 직렬화를 위한 interface
class RecentlyRegisterList(
    val recentlyiamge: Int,
    var recentlyregister_project_name: String?, var recentlyregister_recruitment_position: String?,
    var update_date:String?,
    var stack1:String?,
    var stack2:String?,
    var stack3:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(recentlyiamge)
        parcel.writeString(recentlyregister_project_name)
        parcel.writeString(recentlyregister_recruitment_position)
        parcel.writeString(update_date)
        parcel.writeString(stack1)
        parcel.writeString(stack2)
        parcel.writeString(stack3)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecentlyRegisterList> {
        override fun createFromParcel(parcel: Parcel): RecentlyRegisterList {
            return RecentlyRegisterList(parcel)
        }

        override fun newArray(size: Int): Array<RecentlyRegisterList?> {
            return arrayOfNulls(size)
        }
    }
}
