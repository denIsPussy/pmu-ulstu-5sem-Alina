package com.example.flowershopapp.API.Repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flowershopapp.API.Mediator.BouquetRemoteMediator
import com.example.flowershopapp.API.Model.toBouquet
import com.example.flowershopapp.API.Model.toBouquetRemote
import com.example.flowershopapp.API.MyServerService
import com.example.flowershopapp.Database.AppContainer
import com.example.flowershopapp.Database.AppDatabase
import com.example.flowershopapp.Database.RemoteKeys.Repository.OfflineRemoteKeyRepository
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Repository.Bouquet.BouquetRepository
import com.example.flowershopapp.Entities.Repository.Bouquet.OfflineBouquetRepository
import kotlinx.coroutines.flow.Flow

class RestBouquetRepository(
    private val service: MyServerService,
    private val dbBouquetRepository: OfflineBouquetRepository,
    private val dbRemoteKeyRepository: OfflineRemoteKeyRepository,
    private val orderBouquetRestRepository: RestOrderBouquetRepository,
    private val database: AppDatabase
) : BouquetRepository {
    override fun getAll(): Flow<PagingData<Bouquet>> {
        Log.d(RestBouquetRepository::class.simpleName, "Get Bouquets")

        val pagingSourceFactory = { dbBouquetRepository.getAllBouquetsPagingSource() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = AppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = BouquetRemoteMediator(
                service,
                dbBouquetRepository,
                dbRemoteKeyRepository,
                orderBouquetRestRepository,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getBouquet(uid: Int): Bouquet =
        service.getBouquet(uid).toBouquet()

    override suspend fun insert(Bouquet: Bouquet) {
        service.createBouquet(Bouquet.toBouquetRemote()).toBouquet()
    }

    override suspend fun update(Bouquet: Bouquet) {
        Bouquet.bouquetId?.let { service.updateBouquet(it, Bouquet.toBouquetRemote()).toBouquet() }
    }

    override suspend fun delete(Bouquet: Bouquet) {
        Bouquet.bouquetId?.let { service.deleteBouquet(it).toBouquet() }
    }
}