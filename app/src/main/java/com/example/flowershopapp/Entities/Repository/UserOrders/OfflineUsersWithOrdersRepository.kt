package com.example.flowershopapp.Entities.Repository.UserOrders

import com.example.flowershopapp.Entities.DAO.UsersWithOrders
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef

class OfflineUsersWithOrdersRepository(private val userOrders: UsersWithOrders) : com.example.flowershopapp.Entities.Repository.UserOrders.UsersWithOrdersRepository {
    override suspend fun getAll() = userOrders.getAll()
    override suspend fun insert(user: UserOrderCrossRef) = userOrders.insert(user)
    override suspend fun delete(user: UserOrderCrossRef) = userOrders.delete(user)
    suspend fun insertAll(userOrder: List<UserOrderCrossRef>) =
        userOrders.insert(*userOrder.toTypedArray())
}