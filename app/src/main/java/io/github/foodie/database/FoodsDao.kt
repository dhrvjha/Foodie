package io.github.foodie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodsDao {
    @Insert
    fun insert(foods: FoodsEntity)

    @Query("SELECT * FROM `foods-db` WHERE order_id = :orderId")
    fun getFoodsByID(orderId: Int):List<FoodsEntity>
}