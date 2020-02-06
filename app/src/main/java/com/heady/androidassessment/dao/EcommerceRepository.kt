package com.heady.androidassessment.dao

/**
 * An repository class to manages queries
 */
import android.util.Log
import androidx.lifecycle.LiveData
import com.app.myapplication.model.Categories

class EcommerceRepository(private val ecommerceDao: EcommerceDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCategory: LiveData<List<Category>> = ecommerceDao.getCategories()

    suspend fun insert(categories: Category) {
        ecommerceDao.insertCategories(categories)
    }

    var allProducts: LiveData<List<Product>> = ecommerceDao.getProducts()


    suspend fun insert(product: Product) {
        ecommerceDao.insertProducts(product)
    }

    suspend fun updateMostViewed(most_viewed_ranking : Int,productId : Int) {
        ecommerceDao.updateProductsMostViewed(most_viewed_ranking,productId)
    }

    suspend fun updateMostOrdered(most_ordered_ranking : Int,productId : Int) {
        ecommerceDao.updateProductsMostOrdered(most_ordered_ranking,productId)
    }

    suspend fun updateMostShared(most_shared_ranking : Int,productId : Int) {
        ecommerceDao.updateProductsMostShared(most_shared_ranking,productId)
    }

     fun getMost(most_ranking : String): List<Product>{
        return ecommerceDao.getMostViewedDescending()

    }


}