package com.example.flowershopapp.ComposeUI.Bouquet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.flowershopapp.Database.AppDataContainer
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Repository.Bouquet.BouquetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BouquetCatalogViewModel(
    private val bouquetRepository: BouquetRepository
) : ViewModel() {
    val bouquetListUiState: Flow<PagingData<Bouquet>> = bouquetRepository.getAll()
}