package com.example.commonwallet.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DescriptionSuggestion(
    @PrimaryKey val name: String
)
