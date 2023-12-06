package com.example.flowershopapp.API.Repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flowershopapp.API.Mediator.UserRemoteMediator
import com.example.flowershopapp.API.Model.toUser
import com.example.flowershopapp.API.Model.toUserOrder
import com.example.flowershopapp.API.Model.toUserRemote
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.Database.AppContainer
import com.example.flowershopapp.Database.AppDatabase
import com.example.flowershopapp.Database.RemoteKeys.Repository.OfflineRemoteKeyRepository
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import com.example.flowershopapp.Entities.Repository.User.UserRepository
import com.example.flowershopapp.Entities.Repository.User.OfflineUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestUserRepository(
    private val service: MyServerService,
    private val dbUserRepository: OfflineUserRepository,
    private val dbRemoteKeyRepository: OfflineRemoteKeyRepository,
    private val userOrderRestRepository: RestUserOrderRepository,
    private val database: AppDatabase
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(): Flow<PagingData<User>> {
        Log.d(RestUserRepository::class.simpleName, "Get Users")

        val pagingSourceFactory = { dbUserRepository.getAllUsersPagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = AppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(
                service,
                dbUserRepository,
                dbRemoteKeyRepository,
                userOrderRestRepository,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getUser(userName: String): User =
        service.getUser(userName).toUser()

    override suspend fun getUserWithOrders(userName: String): Flow<UsersWithOrders> {
        return flowOf(service.getUserWithOrders(userName).toUserOrder())
    }

    override suspend fun getUsersWithOrders(): Flow<List<UsersWithOrders>> {
        return flowOf(service.getUsersWithOrders().map { it.toUserOrder() })
    }

    override suspend fun insert(User: User) {
        service.createUser(User.toUserRemote()).toUser()
    }

    override suspend fun update(User: User) {
        User.userId?.let { service.updateUser(it, User.toUserRemote()).toUser() }
    }

    override suspend fun delete(User: User) {
        User.userId?.let { service.deleteUser(it).toUser() }
    }
}