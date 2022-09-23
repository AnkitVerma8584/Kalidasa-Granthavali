package com.kalidasagranthavali.ass.domain.modals

data class HomeFiles(
    val id: Int,
    val cat_id: Int,
    val sub_cat_id: Int,
    val name: String,
    val image: String,
    val file_url: String
)
