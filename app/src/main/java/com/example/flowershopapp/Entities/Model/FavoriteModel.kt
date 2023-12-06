package com.example.flowershopapp.Entities.Model

import androidx.lifecycle.ViewModel
import com.example.flowershopapp.R

class FavoriteModel : ViewModel() {
    companion object {
        private var favoriteList = mutableListOf<Bouquet>()

        val bouquets: List<Bouquet>
            get() = favoriteList.toList()

        fun addBouquet(bouquet: Bouquet): Int{
            return if (favoriteList.contains(bouquet)) {
                removeBouquets(bouquet)
                R.drawable.heart_black
            } else {
                favoriteList.add(bouquet)
                R.drawable.heart_red
            }
        }

        fun addBouquets(vararg bouquets: Bouquet) {
            favoriteList.addAll(bouquets)
        }

        fun removeBouquets(bouquetRemove: Bouquet) : List<Bouquet> {
            var list : MutableList<Bouquet> = mutableListOf()
            favoriteList.forEach{ bouquet ->
                if (bouquet.bouquetId != bouquetRemove.bouquetId) list.add(bouquet)
            }
            favoriteList = list
            return favoriteList
        }

        fun containsBouquet(bouquet: Bouquet): Int{
            if (favoriteList.contains(bouquet)) return R.drawable.heart_red
            return R.drawable.heart_black
        }

        fun clearBouquets() {
            favoriteList.clear()
        }
    }
}