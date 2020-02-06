package com.app.myapplication.model

import com.google.gson.annotations.SerializedName

class EcommerceData (

    @SerializedName("categories") val categories : List<Categories>,
    @SerializedName("rankings") val rankings : List<Rankings>
)