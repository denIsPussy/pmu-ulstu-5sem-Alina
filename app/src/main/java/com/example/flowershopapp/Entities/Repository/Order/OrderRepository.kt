package com.example.flowershopapp.Entities.Repository.Order

import androidx.paging.PagingData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAll(): Flow<PagingData<Order>>
    suspend fun getOrdersWithBouquet(): Flow<List<OrdersWithBouquets>>
    suspend fun getOrderWithBouquet(id: Int): OrdersWithBouquets
    suspend fun insert(order: Order)
    suspend fun update(order: Order)
    suspend fun delete(order: Order)
}