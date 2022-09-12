package com.kalidasagranthavali.ass.domain.utils

data class Result<T>(
    val data: T? = null,
    val message: String = ""
)