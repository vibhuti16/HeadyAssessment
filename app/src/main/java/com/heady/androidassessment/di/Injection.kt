package com.heady.androidassessment.di

import com.heady.androidassessment.model.EcommerceDataSource
import com.heady.androidassessment.model.MuseumRepository

object Injection {

    fun providerRepository():EcommerceDataSource{
        return MuseumRepository()
    }
}