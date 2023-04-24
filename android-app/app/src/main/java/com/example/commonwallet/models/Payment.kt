package com.example.commonwallet.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity
data class Payment(
    @SerializedName("payment_id") @PrimaryKey val paymentId: Int,
    @SerializedName("payer_name") val payerName: String,
    @SerializedName("payment_time") val paymentTime: ZonedDateTime,
    val amount: Double,
    val description: String
)
