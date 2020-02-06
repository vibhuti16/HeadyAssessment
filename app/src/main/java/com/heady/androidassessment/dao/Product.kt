package com.heady.androidassessment.dao

/**
 * An entity class to define a Product sqlite(Room) table
 */

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "product", foreignKeys = arrayOf(ForeignKey(entity = Category::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("category_id"),
        onDelete = ForeignKey.CASCADE)))
class Product(@PrimaryKey @ColumnInfo(name = "id") val id: Int, @ColumnInfo(name = "name") val name: String,
              @ColumnInfo(name = "date_added") val date_added: String,
              @ColumnInfo(name = "tax_name") val tax_name: String,
              @ColumnInfo(name = "tax_value") val tax_value: Double,
              @ColumnInfo(name = "category_id") val category_id: Int,
              @ColumnInfo(name = "category_name") val category_name: String

) {
    @ColumnInfo(name = "variants_id")
    public var variants_id: String = "";

    @ColumnInfo(name = "variants_color")
    public var variants_color: String = "";

    @ColumnInfo(name = "variants_size")
    public var variants_size: String = "";

    @ColumnInfo(name = "variants_price")
    public var variants_price: String = "";

    @ColumnInfo(name = "most_viewed_ranking")
    public var most_viewed_ranking: Int = 1;

    @ColumnInfo(name = "most_ordered_ranking")
    public var most_ordered_ranking: Int = 1;

    @ColumnInfo(name = "most_shared_ranking")
    public var most_shared_ranking: Int = 1;



}


