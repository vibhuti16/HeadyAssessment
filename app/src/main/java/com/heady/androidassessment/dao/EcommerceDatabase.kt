package com.heady.androidassessment.dao

/**
 * An database class to define a Ecommerce database
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Category::class,Product::class), version = 1, exportSchema = false)
public abstract class EcommerceDatabase : RoomDatabase() {

    abstract fun getDao(): EcommerceDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EcommerceDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): EcommerceDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        EcommerceDatabase::class.java,
                        "word_database"
                ).addCallback(EcommerceDatabaseCallBack(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class EcommerceDatabaseCallBack(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.getDao())
                }
            }
        }

        suspend fun populateDatabase(categoryDao: EcommerceDao) {
            // Delete all content here.
            categoryDao.deleteCategories()

            // Add categories



        }
    }
}