package io.github.foodie.database

import androidx.room.Database

@Database(entities = [FoodsEntity::class], version = 1)
abstract class FoodsDatabase {
    abstract fun foodsDao(): FoodsDao
}