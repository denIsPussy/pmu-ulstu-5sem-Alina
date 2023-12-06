package com.example.flowershopapp.Entities.Repository.UserOrders

import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import com.example.flowershopapp.Entities.Model.UsersWithOrders

interface UsersWithOrdersRepository {
    suspend fun getAll(): List<UsersWithOrders>
    suspend fun insert(user: UserOrderCrossRef)
    suspend fun delete(user: UserOrderCrossRef)
}