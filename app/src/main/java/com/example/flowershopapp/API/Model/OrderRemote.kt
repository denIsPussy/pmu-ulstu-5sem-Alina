package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderRemote(
    val orderId: Int? = 0,
    val date: String = "",
    val sum: Int = 0,
)

fun OrderRemote.toOrder(): Order = Order(
    orderId,
    date,
    sum
)

fun Order.toOrderRemote(): OrderRemote = OrderRemote(
    orderId,
    date,
    sum
)