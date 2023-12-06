package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRemote(
    val userId: Int? = 0,
    val userName: String = "",
    val dateOfBirth: String = "",
    val phoneNumber: String = "",
    val password: String = ""
)

fun UserRemote.toUser(): User = User(
    userId,
    userName,
    dateOfBirth,
    phoneNumber,
    password
)

fun User.toUserRemote(): UserRemote = UserRemote(
    userId,
    userName,
    dateOfBirth,
    phoneNumber,
    password
)