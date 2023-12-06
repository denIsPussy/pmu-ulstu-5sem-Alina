package com.example.flowershopapp.API.Mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.flowershopapp.API.Model.toUser
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.API.Repository.RestOrderBouquetRepository
import com.example.flowershopapp.API.Repository.RestUserOrderRepository
import com.example.flowershopapp.Database.AppDatabase
import com.example.flowershopapp.Database.RemoteKeys.Model.RemoteKeyType
import com.example.flowershopapp.Database.RemoteKeys.Model.RemoteKeys
import com.example.flowershopapp.Database.RemoteKeys.Repository.OfflineRemoteKeyRepository
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Repository.User.OfflineUserRepository
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val service: MyServerService,
    private val dbUserRepository: OfflineUserRepository,
    private val dbRemoteKeyRepository: OfflineRemoteKeyRepository,
    private val userOrderRestRepository: RestUserOrderRepository,
    private val database: AppDatabase
) : RemoteMediator<Int, User>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val Users = service.getUsers().map { it.toUser() }
            val endOfPaginationReached = Users.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dbRemoteKeyRepository.deleteRemoteKey(RemoteKeyType.ORDER)
                    dbUserRepository.deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = Users.map {
                    it.userId?.let { it1 ->
                        RemoteKeys(
                            entityId = it1,
                            type = RemoteKeyType.ORDER,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
                userOrderRestRepository.getAll()
                dbRemoteKeyRepository.createRemoteKeys(keys)
                dbUserRepository.insertUsers(Users)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { User ->
                User.userId?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.ORDER) }
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { User ->
                User.userId?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.ORDER) }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, User>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.userId?.let { UserUid ->
                dbRemoteKeyRepository.getAllRemoteKeys(UserUid, RemoteKeyType.ORDER)
            }
        }
    }
}