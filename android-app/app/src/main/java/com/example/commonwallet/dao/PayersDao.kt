package com.example.commonwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.commonwallet.viewmodels.Payer

@Dao
interface PayersDao {
    @Query("SELECT * FROM payer")
    suspend fun getAll(): List<Payer>

    @Insert
    suspend fun insertAll(payers: List<Payer>)

    @Query("DELETE FROM payer")
    suspend fun deleteAll()
}