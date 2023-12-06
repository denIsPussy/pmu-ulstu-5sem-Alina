package com.example.flowershopapp.Entities.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flowershopapp.Entities.Model.OrderBouquetCrossRef
import com.example.flowershopapp.Entities.Model.UserOrderCrossRef
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersWithOrders {
    @Query("select * from users")
    fun getAll(): List<UsersWithOrders>
    @Insert
    suspend fun insert(vararg user: UserOrderCrossRef)
    @Delete
    suspend fun delete(user: UserOrderCrossRef)
}