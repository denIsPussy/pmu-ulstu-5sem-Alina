package com.example.flowershopapp.Entities.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersWithBouquet {
    @Query("select * from orders")
    fun getAll(): List<OrdersWithBouquets>
    @Insert
    suspend fun insert(vararg order: OrderBouquetCrossRef)

    @Delete
    suspend fun delete(order: OrderBouquetCrossRef)
}