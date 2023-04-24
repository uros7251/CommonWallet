package com.example.commonwallet.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.commonwallet.dao.DescriptionSuggestionsDao
import com.example.commonwallet.dao.PayersDao
import com.example.commonwallet.dao.PaymentsDao
import com.example.commonwallet.dao.StatsDao
import com.example.commonwallet.models.DescriptionSuggestion
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.viewmodels.Payer

@Database(
    entities = [TotalAndNetPaymentStat::class, Payment::class, Payer::class, DescriptionSuggestion::class],
    version = 1,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun statsDao(): StatsDao
    abstract fun payersDao(): PayersDao
    abstract fun descriptionSuggestionsDao(): DescriptionSuggestionsDao
    abstract fun paymentsDao(): PaymentsDao
}