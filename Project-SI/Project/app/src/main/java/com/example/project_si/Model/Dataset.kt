package com.example.project_si.model

import android.os.Parcel
import android.os.Parcelable

data class Dataset(
    val id: String? = null,
    val nama_project: String = "",
    val fileUrl: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama_project)
        parcel.writeString(fileUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dataset> {
        override fun createFromParcel(parcel: Parcel): Dataset {
            return Dataset(parcel)
        }

        override fun newArray(size: Int): Array<Dataset?> {
            return arrayOfNulls(size)
        }
    }
}
