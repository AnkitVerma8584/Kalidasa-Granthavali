package com.kalidasagranthavali.ass.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_category")
class SubCategory(
    @PrimaryKey val id: Int,
    val cat_id: Int,
    val name: String,
    val description: String,
    val image: String
)