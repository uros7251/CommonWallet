package com.example.commonwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.commonwallet.models.OutstandingPayment

@Dao
interface OutstandingPaymentsDao {
    @Insert
    suspend fun insert(payment: OutstandingPayment)
    @Query("DELETE FROM outstandingPayment")
    suspend fun deleteAll()
    @Delete
    suspend fun delete(outstandingPayment: OutstandingPayment)
    @Query("SELECT * FROM outstandingPayment WHERE walletId = :walletId")
    suspend fun getAll(walletId: Int): List<OutstandingPayment>
}