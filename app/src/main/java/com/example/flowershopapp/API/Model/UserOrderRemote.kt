package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.serialization.Serializable

@Serializable
data class UserOrderRemote(
    val user: UserRemote = UserRemote(),
    val order: List<OrderRemote> = listOf()
)

fun UserOrderRemote.toUserOrder(): UsersWithOrders{
    val convertedUser = this.user.toUser()
    val convertedOrders = this.order.map { it.toOrder() }
    return UsersWithOrders(convertedUser, convertedOrders)
}

fun UsersWithOrders.toUserOrderRemote(): UserOrderRemote{
    val convertedUser = this.user.toUserRemote()
    val convertedOrders = this.orders.map { it.toOrderRemote() }
    return UserOrderRemote(convertedUser, convertedOrders)
}
