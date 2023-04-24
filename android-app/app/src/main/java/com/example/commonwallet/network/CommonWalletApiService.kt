package com.example.commonwallet.network

import com.example.commonwallet.models.NewPayment
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.viewmodels.Payer
import retrofit2.Response
import retrofit2.http.*

interface CommonWalletApiService {
    @POST("newpayment")
    suspend fun registerNewPayment(@Body payment: NewPayment): Response<Unit>

    @GET("wallet/{wallet_id}/stats")
    suspend fun listTotalAndNetPayments(@Path("wallet_id") walletId: Int): Response<List<TotalAndNetPaymentStat>>

    @GET("wallet/{wallet_id}/participants")
    suspend fun listParticipants(@Path("wallet_id") walletId: Int): Response<List<Payer>>

    @GET("wallet/{wallet_id}/descriptions")
    suspend fun listSuggestedDescriptions(@Path("wallet_id") walletId: Int): Response<List<String>>

    @GET("wallet/{wallet_id}/payments")
    suspend fun listLatestPayments(@Path("wallet_id") walletId: Int, @Query("n") n: Int): Response<List<Payment>>
}