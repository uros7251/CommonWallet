package com.example.commonwallet.data.repository

import com.example.commonwallet.models.OutstandingPayment
import com.example.commonwallet.models.Payer
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat

interface WalletRepository {
    suspend fun submitNewPayment(outstandingPayment: OutstandingPayment): Boolean
    suspend fun anyOutstandingPayment(walletId: Int): Boolean
    suspend fun uploadOutstandingPayments(walletId: Int): Boolean
    suspend fun listWalletParticipants(walletId: Int, refresh: Boolean = false): List<Payer>
    suspend fun listDescriptionSuggestions(walletId: Int, refresh: Boolean = false) : List<String>
    suspend fun listStats(walletId: Int, refresh: Boolean = false): List<TotalAndNetPaymentStat>
    suspend fun listLatestPayments(walletId: Int, latestN: Int, refresh: Boolean = false): List<Payment>
}