package com.example.flowershopapp.API.Model

import com.example.flowershopapp.Entities.Model.Bouquet
import kotlinx.serialization.Serializable

@Serializable
data class BouquetRemote(
    val bouquetId: Int? = 0,
    val name: String = "",
    val quantityOfFlowers: Int = 0,
    val price: Int = 0,
    val image: ByteArray? = null,
)

fun BouquetRemote.toBouquet(): Bouquet = Bouquet(
    bouquetId,
    name,
    quantityOfFlowers,
    price,
    image
)

fun Bouquet.toBouquetRemote(): BouquetRemote = BouquetRemote(
    bouquetId,
    name,
    quantityOfFlowers,
    price,
    image
)