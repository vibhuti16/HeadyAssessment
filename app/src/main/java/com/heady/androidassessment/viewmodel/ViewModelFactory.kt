package com.heady.androidassessment.publicdel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heady.androidassessment.model.EcommerceDataSource
import com.heady.androidassessment.viewmodel.EcommerceViewModel

class ViewModelFactory(val context: Context,private val repository:EcommerceDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EcommerceViewModel(repository,context!!) as T
    }
}