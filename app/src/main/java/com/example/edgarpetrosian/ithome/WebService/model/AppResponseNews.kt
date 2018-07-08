package com.example.edgarpetrosian.ithome.WebService.model

import android.os.Parcel
import android.os.Parcelable

data class AppResponseNews(
        val id: String,
        val title: String,
        val description: String,
        val detailText: String,
        val postImageUrl: String,
        val postVideoUrl: String,
        val category: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(detailText)
        parcel.writeString(postImageUrl)
        parcel.writeString(postVideoUrl)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppResponseNews> {
        override fun createFromParcel(parcel: Parcel): AppResponseNews {
            return AppResponseNews(parcel)
        }

        override fun newArray(size: Int): Array<AppResponseNews?> {
            return arrayOfNulls(size)
        }
    }
}