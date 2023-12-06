package com.example.flowershopapp.Entities.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int?,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "sum")
    val sum: Int
) {

    @Ignore
    constructor(
        date: String,
        sum: Int,
    ) : this(null, date, sum)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Order
        if (orderId != other.orderId) return false
        return true
    }

    override fun hashCode(): Int {
        return orderId ?: -1
    }
}
