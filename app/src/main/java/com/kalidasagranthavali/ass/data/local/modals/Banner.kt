package com.kalidasagranthavali.ass.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner_images")
data class Banner(
    @PrimaryKey
    val image: String
)
