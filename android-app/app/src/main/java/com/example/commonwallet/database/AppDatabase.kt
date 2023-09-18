package com.example.commonwallet.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.commonwallet.dao.*
import com.example.commonwallet.models.*

@Database(
    entities = [TotalAndNetPaymentStat::class, Payment::class, Payer::class, DescriptionSuggestion::class, OutstandingPayment::class],
    version = 3,
    autoMigrations = [
        AutoMigration ( from = 1, to = 3)],
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun statsDao(): StatsDao
    abstract fun payersDao(): PayersDao
    abstract fun descriptionSuggestionsDao(): DescriptionSuggestionsDao
    abstract fun paymentsDao(): PaymentsDao
    abstract fun outstandingPaymentsDao(): OutstandingPaymentsDao
}