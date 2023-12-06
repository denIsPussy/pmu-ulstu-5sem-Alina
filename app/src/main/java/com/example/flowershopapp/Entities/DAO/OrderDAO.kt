package com.example.flowershopapp.Entities.DAO

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDAO {
    @Query("select * from orders")
    fun getAll(): PagingSource<Int, Order>

    @Query("select * from orders")
    fun getOrdersWithBouquet(): Flow<List<OrdersWithBouquets>>

    @Query("select * from orders where orderId = :id")
    fun getOrderWithBouquet(id: Int): OrdersWithBouquets

    @Insert
    suspend fun insert(vararg order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("DELETE FROM orders")
    suspend fun deleteAll()
}