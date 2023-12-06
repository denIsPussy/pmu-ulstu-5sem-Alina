package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import kotlinx.serialization.Serializable

@Serializable
data class UserOrderCrossRefRemote(
    val userId: Int = 0,
    val orderId: Int = 0,
)

fun UserOrderCrossRefRemote.UserOrderCrossRef(): UserOrderCrossRef = UserOrderCrossRef(
    userId,
    orderId
)

fun UserOrderCrossRef.UserOrderCrossRefRemote(): UserOrderCrossRefRemote = UserOrderCrossRefRemote(
    userId,
    orderId
)