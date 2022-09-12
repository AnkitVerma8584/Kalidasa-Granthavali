package com.kalidasagranthavali.ass.domain.utils

data class ResultList<T>(
    val data: List<T>? = null,
    val message: String = ""
)