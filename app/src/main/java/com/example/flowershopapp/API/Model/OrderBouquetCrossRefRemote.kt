package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import kotlinx.serialization.Serializable

@Serializable
data class OrderBouquetCrossRefRemote(
    val orderId: Int = 0,
    val bouquetId: Int = 0,
)

fun OrderBouquetCrossRefRemote.UserOrderCrossRef(): OrderBouquetCrossRef = OrderBouquetCrossRef(
    orderId,
    bouquetId
)

fun OrderBouquetCrossRef.OrderBouquetCrossRefRemote(): OrderBouquetCrossRefRemote = OrderBouquetCrossRefRemote(
    orderId,
    bouquetId
)