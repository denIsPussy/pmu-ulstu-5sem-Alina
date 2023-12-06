package com.example.flowershopapp.Entities.Model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "orderId"])
class UserOrderCrossRef (
    val userId: Int,
    val orderId: Int
)