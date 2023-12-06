package com.example.flowershopapp.Entities.Repository.Order

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.flowershopapp.Database.AppContainer
import com.example.flowershopapp.Entities.DAO.OrderDAO
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.OrdersWithBouquets
import kotlinx.coroutines.flow.Flow

class OfflineOrderRepository(private val orderDAO: OrderDAO) : OrderRepository {
    override fun getAll(): Flow<PagingData<Order>> = Pager(
        config = PagingConfig(
            pageSize = AppContainer.LIMIT,
            enablePlaceholders = false
        ),
        pagingSourceFactory = orderDAO::getAll
    ).flow
    override suspend fun getOrdersWithBouquet(): Flow<List<OrdersWithBouquets>> = orderDAO.getOrdersWithBouquet()
    override suspend fun getOrderWithBouquet(id: Int): OrdersWithBouquets = orderDAO.getOrderWithBouquet(id)
    override suspend fun insert(order: Order) = orderDAO.insert(order)
    override suspend fun update(order: Order) = orderDAO.update(order)
    override suspend fun delete(order: Order) = orderDAO.delete(order)
    fun getAllOrdersPagingSource(): PagingSource<Int, Order> = orderDAO.getAll()
    suspend fun deleteAll() = orderDAO.deleteAll()
    suspend fun insertOrders(orders: List<Order>) =
        orderDAO.insert(*orders.toTypedArray())
}