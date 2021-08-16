package io.github.foodie.database

import androidx.room.*
import java.util.*

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM 'order-db' ORDER BY id DESC")
    fun getAllOrders(): List<OrderEntity>
}

