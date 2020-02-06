package com.app.myapplication.model

import com.google.gson.annotations.SerializedName


data class Categories (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("products") val products : List<Products>,
    @SerializedName("child_categories") val child_categories : List<String>
)
