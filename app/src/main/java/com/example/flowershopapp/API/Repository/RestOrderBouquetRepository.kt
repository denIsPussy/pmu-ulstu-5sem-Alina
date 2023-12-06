package com.example.flowershopapp.API.Repository

import android.util.Log
import com.example.flowershopapp.API.Model.OrderBouquetCrossRefRemote
import com.example.flowershopapp.API.Model.toOrderBouquetRemote
import com.example.flowershopapp.API.Model.toOrdersWithBouquets
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import com.example.flowershopapp.Entities.Repository.OrderBouquets.OfflineOrdersWithBouquetsRepository
import com.example.flowershopapp.Entities.Repository.OrderBouquets.OrdersWithBouquetsRepository

class RestOrderBouquetRepository(
    private val service: MyServerService,
    private val dbOrderBouquetRepository: OfflineOrdersWithBouquetsRepository,
): OrdersWithBouquetsRepository {
    override suspend fun getAll(): List<OrdersWithBouquets> {
        Log.d(RestOrderBouquetRepository::class.simpleName, "Get OrderBouquets")

        val existOrderBouquets = dbOrderBouquetRepository.getAll().associateBy { it.order.orderId }.toMutableMap()

        service.getOrdersWithBouquets()
            .map { it.toOrdersWithBouquets() }
            .forEach { orderBouquet ->
                val existOrderBouquet = existOrderBouquets[orderBouquet.order.orderId]
                if (existOrderBouquet == null) {
                    dbOrderBouquetRepository.insertAll(orderBouquet.bouquets.map { OrderBouquetCrossRef(
                        orderBouquet.order.orderId!!, it.bouquetId!!
                    ) })
                }
                else if (orderBouquet.bouquets.sortedBy { it.bouquetId } != existOrderBouquet.bouquets.sortedBy { it.bouquetId }){
                    orderBouquet.bouquets.map { if (!existOrderBouquet.bouquets.contains(it)) dbOrderBouquetRepository.insert(
                        OrderBouquetCrossRef(orderBouquet.order.orderId!!, it.bouquetId!!)
                    ) }
                }
                existOrderBouquets[orderBouquet.order.orderId] = orderBouquet
            }

        return existOrderBouquets.map { it.value }.sortedBy { it.order.orderId }
    }
    override suspend fun insert(orderBouquet: OrderBouquetCrossRef) {
        service.createOrderBouquet(orderBouquet.OrderBouquetCrossRefRemote())
    }

    override suspend fun delete(orderBouquet: OrderBouquetCrossRef) {
        service.deleteOrderBouquet(orderBouquet.orderId)
    }
}