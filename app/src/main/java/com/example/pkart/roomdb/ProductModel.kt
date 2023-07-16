package com.example.pkart.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "Products")
data class ProductModel(
    @PrimaryKey
    val productId : String,
    @ColumnInfo(name = "ProductName")
    val productInfo : String?=  "",
    @ColumnInfo(name = "ProductImage")
    val productImage : String?=  "",
    @ColumnInfo(name = "ProductSP")
    val productSP : String?=  "",
)
