package com.example.flowershopapp.Entities.Model

import androidx.lifecycle.ViewModel

class CartModel : ViewModel() {
    companion object {
        private var bouquetsList = mutableListOf<Bouquet>()

        val bouquets: List<Bouquet>
            get() = bouquetsList.toList()

        fun addBouquet(bouquet: Bouquet) {
            bouquetsList.add(bouquet)
        }

        fun addBouquets(vararg bouquets: Bouquet) {
            bouquetsList.addAll(bouquets)
        }

        fun removeBouquets(bouquetRemove: Bouquet) : List<Bouquet> {
            var list : MutableList<Bouquet> = mutableListOf()
            bouquetsList.forEach{ bouquet ->
                if (bouquet.bouquetId != bouquetRemove.bouquetId) list.add(bouquet)
            }
            bouquetsList = list
            return bouquetsList
        }

        fun clearBouquets() {
            bouquetsList.clear()
        }
    }
}