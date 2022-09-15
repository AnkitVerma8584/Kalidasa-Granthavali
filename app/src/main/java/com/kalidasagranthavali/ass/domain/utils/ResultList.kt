package com.kalidasagranthavali.ass.domain.utils

data class ResultList<T>(
    val success: Boolean,
    val data: List<T>? = null,
    val message: String = ""
)