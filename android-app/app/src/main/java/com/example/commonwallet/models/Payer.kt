package com.example.commonwallet.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payer(
    @PrimaryKey val id: Int,
    val name: String
)
