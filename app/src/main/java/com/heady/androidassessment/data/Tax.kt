package com.app.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tax (

    @SerializedName("name") val name : String,
    @SerializedName("value") val value : Double
) : Parcelable