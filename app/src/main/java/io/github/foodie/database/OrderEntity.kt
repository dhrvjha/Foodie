package io.github.foodie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "order-db")
data class OrderEntity(
        @PrimaryKey val id: Int,
        @ColumnInfo(name="date") val date: Date,
        @ColumnInfo(name="restaurant_id") val restaurant_id: Int
)