package com.example.flowershopapp.API.Repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flowershopapp.API.Mediator.OrderRemoteMediator
import com.example.flowershopapp.API.Model.toOrder
import com.example.flowershopapp.API.Model.toOrderRemote
import com.example.flowershopapp.API.Model.toOrdersWithBouquets
import com.example.flowershopapp.API.Model.toUserOrder
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.Database.AppContainer
import com.example.flowershopapp.Database.AppDatabase
import com.example.flowershopapp.Database.RemoteKeys.Repository.OfflineRemoteKeyRepository
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import com.example.flowershopapp.Entities.Repository.Order.OrderRepository
import com.example.flowershopapp.Entities.Repository.Order.OfflineOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestOrderRepository(
    private val service: MyServerService,
    private val dbOrderRepository: OfflineOrderRepository,
    private val dbRemoteKeyRepository: OfflineRemoteKeyRepository,
    private val database: AppDatabase
) : OrderRepository {
    override fun getAll(): Flow<PagingData<Order>> {
        Log.d(RestOrderRepository::class.simpleName, "Get Orders")

        val pagingSourceFactory = { dbOrderRepository.getAllOrdersPagingSource() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = AppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = OrderRemoteMediator(
                service,
                dbOrderRepository,
                dbRemoteKeyRepository,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getOrdersWithBouquet(): Flow<List<OrdersWithBouquets>> {
        return flowOf(service.getOrdersWithBouquets().map { it.toOrdersWithBouquets() })
    }

    override suspend fun getOrderWithBouquet(id: Int): OrdersWithBouquets {
        return service.getOrderWithBouquets(id).toOrdersWithBouquets()
    }

    override suspend fun insert(Order: Order) {
        service.createOrder(Order.toOrderRemote()).toOrder()
    }

    override suspend fun update(order: Order) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(Order: Order) {
        Order.orderId?.let { service.deleteOrder(it).toOrder() }
    }
}