package com.example.commonwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.commonwallet.models.DescriptionSuggestion

@Dao
interface DescriptionSuggestionsDao {
    @Insert
    suspend fun insertAll(descriptions: List<DescriptionSuggestion>)

    @Query("DELETE FROM descriptionSuggestion")
    suspend fun deleteAll()

    @Query("SELECT name FROM descriptionSuggestion")
    suspend fun getAll(): List<String>
}