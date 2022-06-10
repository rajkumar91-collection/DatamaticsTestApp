package com.rajkumar.datamaticsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topping (
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("type")
    var type: String?

) : Parcelable
