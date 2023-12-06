package com.example.flowershopapp.Entities.DAO

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("select * from users")
    fun getAll(): PagingSource<Int, User>

    @Query("select * from users where name = :userName")
    fun getUser(userName: String): User

    @Query("select * from users where name = :userName")
    fun getUserWithOrders(userName: String): Flow<UsersWithOrders>

    @Query("select * from users")
    fun getUsersWithOrders(): Flow<List<UsersWithOrders>>

    @Insert
    suspend fun insert(vararg user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}