package com.heady.androidassessment.model

import android.util.Log
import com.app.myapplication.model.EcommerceData
import com.heady.androidassessment.data.ApiClient
import com.heady.androidassessment.data.EcommerceResponse
import com.heady.androidassessment.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG="CONSOLE"

class MuseumRepository:EcommerceDataSource {

    private var call:Call<EcommerceData>?=null

    override fun retrieveJson(callback: OperationCallback) {
        call=ApiClient.build()?.ecommerceData()

        call?.enqueue(object :Callback<EcommerceData>{
            override fun onFailure(call: Call<EcommerceData>, t: Throwable) {
                Log.d("failure",t.message)
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<EcommerceData>, response: Response<EcommerceData>) {

                response?.body()?.let {
                    if(response.isSuccessful){

                        callback.onSuccess(it)
                    }else{
                        callback.onError("error")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}