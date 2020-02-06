package com.heady.androidassessment.model

import com.heady.androidassessment.data.OperationCallback

interface EcommerceDataSource {

    fun retrieveJson(callback: OperationCallback)
    fun cancel()
}