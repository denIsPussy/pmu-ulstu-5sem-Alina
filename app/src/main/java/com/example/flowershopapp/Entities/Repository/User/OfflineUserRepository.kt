package com.example.flowershopapp.Entities.Repository.User

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.flowershopapp.Database.AppContainer
import com.example.flowershopapp.Entities.DAO.UserDAO
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDAO: UserDAO) : UserRepository{
    override fun getAll(): Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = AppContainer.LIMIT,
            enablePlaceholders = false
        ),
        pagingSourceFactory = userDAO::getAll
    ).flow
    override suspend fun getUser(userName: String): User = userDAO.getUser(userName)
    override suspend fun getUserWithOrders(userName: String): Flow<UsersWithOrders> = userDAO.getUserWithOrders(userName)
    override suspend fun getUsersWithOrders(): Flow<List<UsersWithOrders>> = userDAO.getUsersWithOrders()
    override suspend fun insert(user: User) = userDAO.insert(user)
    override suspend fun update(user: User) = userDAO.update(user)
    override suspend fun delete(user: User) = userDAO.delete(user)
    fun getAllUsersPagingSource(): PagingSource<Int, User> = userDAO.getAll()
    suspend fun deleteAll() = userDAO.deleteAll()
    suspend fun insertUsers(users: List<User>) =
        userDAO.insert(*users.toTypedArray())
}