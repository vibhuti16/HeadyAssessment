package com.heady.androidassessment.dao

/**
 * An entity class to define a Category sqlite(Room) table
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category(@PrimaryKey @ColumnInfo(name = "id") val id: Int,@ColumnInfo(name = "name") val name: String
               ){


}