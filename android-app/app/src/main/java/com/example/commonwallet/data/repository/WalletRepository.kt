package com.example.commonwallet.data.repository

import com.example.commonwallet.models.NewPayment
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.viewmodels.Payer

interface WalletRepository {
    suspend fun submitNewPayment(payment: NewPayment): Boolean
    suspend fun listWalletParticipants(walletId: Int, refresh: Boolean = false): List<Payer>
    suspend fun listDescriptionSuggestions(walletId: Int, refresh: Boolean = false) : List<String>
    suspend fun listStats(walletId: Int, refresh: Boolean = false): List<TotalAndNetPaymentStat>
    suspend fun listLatestPayments(walletId: Int, latestN: Int, refresh: Boolean = false): List<Payment>
}