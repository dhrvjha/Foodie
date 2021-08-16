package io.github.foodie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="foods-db")
data class FoodsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="order_id") val orderId: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="price") val price: String
) {
}