package com.example.commonwallet.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.commonwallet.models.Payment

@Dao
interface PaymentsDao {
    @Insert
    suspend fun insertAll(payments: List<Payment>)
    @Query("DELETE FROM payment")
    suspend fun deleteAll()
    @Query("SELECT * FROM payment ORDER BY paymentTime DESC")
    suspend fun getAll(): List<Payment>
}