package com.example.commonwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.models.Payer

@Dao
interface StatsDao {
    @Query("SELECT * FROM TotalAndNetPaymentStat")
    suspend fun getAll(): List<TotalAndNetPaymentStat>

    @Insert
    suspend fun insertAll(stats: List<TotalAndNetPaymentStat>)

    @Query("DELETE FROM TotalAndNetPaymentStat")
    suspend fun deleteAll()
}