package com.nik.todolist.Data.intity


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val time: String,
           val text: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}