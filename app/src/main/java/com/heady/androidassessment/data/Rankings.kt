package com.app.myapplication.model

import com.google.gson.annotations.SerializedName


data class Rankings (

    @SerializedName("ranking") val ranking : String,
    @SerializedName("products") val products : List<Products>
)