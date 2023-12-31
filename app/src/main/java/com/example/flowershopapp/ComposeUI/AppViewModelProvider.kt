package com.example.flowershopapp.ComposeUI

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flowershopapp.BouquetApplication
import com.example.flowershopapp.ComposeUI.Bouquet.BouquetCatalogViewModel
import com.example.flowershopapp.ComposeUI.User.OrderViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BouquetCatalogViewModel(bouquetApplication().container.bouquetRestRepository)
        }
        initializer {
            OrderViewModel(bouquetApplication().container.userRestRepository)
        }
    }
}

fun CreationExtras.bouquetApplication(): BouquetApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BouquetApplication)