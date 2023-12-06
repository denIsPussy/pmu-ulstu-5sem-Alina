package com.example.flowershopapp.Entities.Repository.User

import androidx.paging.PagingData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAll(): Flow<PagingData<User>>
    suspend fun getUser(userName: String): User
    suspend fun getUserWithOrders(userName: String): Flow<UsersWithOrders>
    suspend fun getUsersWithOrders(): Flow<List<UsersWithOrders>>
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
}