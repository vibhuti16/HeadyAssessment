package com.heady.androidassessment.data

import com.app.myapplication.model.EcommerceData

data class EcommerceResponse(val data:EcommerceData){
    fun isSuccess():Boolean=true
}