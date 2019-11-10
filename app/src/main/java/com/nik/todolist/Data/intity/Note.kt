package com.nik.todolist.Data.intity


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val time: String = "",
                val text: String = "",
                val id: String = ""
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if(this == other) return true
        if(javaClass != other?.javaClass) return false

        other as Note
        if(id != other.id) return false
        return true
    }
}