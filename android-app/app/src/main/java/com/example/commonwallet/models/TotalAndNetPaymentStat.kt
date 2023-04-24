package com.example.commonwallet.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TotalAndNetPaymentStat(
    @SerializedName("person_id") @PrimaryKey val personId: Int,
    val name: String,
    val total: Double,
    val net: Double
)
