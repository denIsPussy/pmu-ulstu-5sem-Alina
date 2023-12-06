package com.example.flowershopapp.API.Repository

import android.util.Log
import com.example.flowershopapp.API.Model.UserOrderCrossRefRemote
import com.example.flowershopapp.API.Model.toUserOrder
import com.example.flowershopapp.API.Model.toUserOrderRemote
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import com.example.flowershopapp.Entities.Repository.OrderBouquets.OfflineOrdersWithBouquetsRepositoryRepository
import com.example.flowershopapp.Entities.Repository.OrderBouquets.OrdersWithBouquetsRepository
import com.example.flowershopapp.Entities.Repository.UserOrders.OfflineUsersWithOrdersRepository
import com.example.flowershopapp.Entities.Repository.UserOrders.UsersWithOrdersRepository

class RestUserOrderRepository(
    private val service: MyServerService,
    private val dbUserOrderRepository: OfflineUsersWithOrdersRepository,
): UsersWithOrdersRepository {
    override suspend fun getAll(): List<UsersWithOrders> {
        Log.d(RestUserOrderRepository::class.simpleName, "Get UserOrders")

        val existUserOrders = dbUserOrderRepository.getAll().associateBy { it.user.userId }.toMutableMap()

        service.getUsersWithOrders()
            .map { it.toUserOrder() }
            .forEach { userOrder ->
                val existUserOrder = existUserOrders[userOrder.user.userId]
                if (existUserOrder == null) {
                    val userOrdersCrossRefs = userOrder.orders.map { order ->
                        UserOrderCrossRef(userOrder.user.userId!!, order.orderId!!)
                    }
                    dbUserOrderRepository.insertAll(userOrdersCrossRefs)
                }
                else if (userOrder.orders.sortedBy { it.orderId } != existUserOrder.orders.sortedBy { it.orderId }){
                    userOrder.orders.map { if (!existUserOrder.orders.contains(it)) dbUserOrderRepository.insert(
                        UserOrderCrossRef(userOrder.user.userId!!, it.orderId!!)
                    ) }
                }
                existUserOrders[userOrder.user.userId] = userOrder
            }

        return existUserOrders.map { it.value }.sortedBy { it.user.userId }
    }
    override suspend fun insert(userOrder: UserOrderCrossRef) {
        service.createUserOrder(userOrder.UserOrderCrossRefRemote())
    }

    override suspend fun delete(userOrder: UserOrderCrossRef) {
        service.deleteUserOrder(userOrder.orderId)
    }
}