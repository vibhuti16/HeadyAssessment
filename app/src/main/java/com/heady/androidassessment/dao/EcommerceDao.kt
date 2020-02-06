package com.heady.androidassessment.dao

/**
 * An dao class to query, insert and delete entities
 */
import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.myapplication.model.Categories

@Dao
interface EcommerceDao {

    @Query("SELECT * from category ORDER BY name ASC")
    fun getCategories(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: Category)

    @Query("DELETE FROM category")
    suspend fun deleteCategories()

    @Query("SELECT * from product order by category_id")
    fun getProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(word: Product)

    @Query("UPDATE product SET most_viewed_ranking=:most_viewed_ranking WHERE id = :productId")
    suspend fun updateProductsMostViewed(most_viewed_ranking : Int,productId : Int)

    @Query("UPDATE product SET most_ordered_ranking=:most_ordered_ranking WHERE id = :productId")
    suspend fun updateProductsMostOrdered(most_ordered_ranking : Int,productId : Int)

    @Query("UPDATE product SET most_shared_ranking=:most_shared_ranking WHERE id = :productId")
    suspend fun updateProductsMostShared(most_shared_ranking : Int,productId : Int)

    @Query("DELETE FROM category")
    suspend fun deleteProducts()

    @Query("SELECT * from product order by most_viewed_ranking desc,category_id")
    fun getMostViewedDescending(): List<Product>
}