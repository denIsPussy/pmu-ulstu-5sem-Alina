package com.example.flowershopapp.Entities.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UsersWithOrders(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "orderId",
        associateBy = Junction(UserOrderCrossRef::class)
    )
    val orders: List<Order>
)