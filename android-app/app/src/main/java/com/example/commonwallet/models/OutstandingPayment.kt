package com.example.commonwallet.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

@Entity
data class OutstandingPayment(
    @Expose(serialize = false) @PrimaryKey(autoGenerate = true) val paymentId: Int,
    @SerializedName("wallet_id") val walletId: Int,
    @SerializedName("payer_id") val payerId: Int,
    @SerializedName("payment_time") val paymentTime: ZonedDateTime,
    val amount: Double,
    val description: String
)
