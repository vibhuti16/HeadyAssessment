package com.app.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Variants (

        @SerializedName("id") val id : String,
        @SerializedName("color") val color : String,
        @SerializedName("size") val size : String,
        @SerializedName("price") val price : String
) : Parcelable

