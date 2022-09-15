package com.kalidasagranthavali.ass.domain.utils

data class Result<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null
)