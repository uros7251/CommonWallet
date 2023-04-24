package com.example.commonwallet.models

import com.google.gson.annotations.SerializedName

data class NewPayment(
    @SerializedName("wallet_id") val walletId: Int,
    @SerializedName("payer_id") val payerId: Int,
    val amount: Double,
    val description: String
)
