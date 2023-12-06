package com.example.flowershopapp.Entities.Repository.OrderBouquets

import com.example.flowershopapp.Entities.DAO.OrdersWithBouquet
import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import kotlinx.coroutines.flow.Flow

class OfflineOrdersWithBouquetsRepository(private val orderBouquetsDAO: OrdersWithBouquet) : OrdersWithBouquetsRepository {
    override suspend fun getAll() = orderBouquetsDAO.getAll()
    override suspend fun insert(order: OrderBouquetCrossRef) = orderBouquetsDAO.insert(order)
    override suspend fun delete(order: OrderBouquetCrossRef) = orderBouquetsDAO.delete(order)
    suspend fun insertAll(orderBouquet: List<OrderBouquetCrossRef>) =
        orderBouquetsDAO.insert(*orderBouquet.toTypedArray())
}