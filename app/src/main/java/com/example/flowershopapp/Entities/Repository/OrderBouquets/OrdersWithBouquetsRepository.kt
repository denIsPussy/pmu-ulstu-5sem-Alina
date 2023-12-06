package com.example.flowershopapp.Entities.Repository.OrderBouquets

import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import kotlinx.coroutines.flow.Flow

interface OrdersWithBouquetsRepository {
    suspend fun getAll(): List<OrdersWithBouquets>
    suspend fun insert(order: OrderBouquetCrossRef)
    suspend fun delete(order: OrderBouquetCrossRef)
}