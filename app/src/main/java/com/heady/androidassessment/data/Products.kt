package com.app.myapplication.model

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Products (

        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("date_added") val date_added : String,
        @SerializedName("view_count") val view_count : Int,
        @SerializedName("order_count") val order_count : Int,
        @SerializedName("shares") val shares : Int,
        @SerializedName("tax") val tax : Tax,
        val category : String,
        @SerializedName("variants") val variants : List<Variants>




): Parcelable


