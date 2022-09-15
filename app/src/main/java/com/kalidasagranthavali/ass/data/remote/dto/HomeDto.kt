package com.kalidasagranthavali.ass.data.remote.dto

import com.kalidasagranthavali.ass.domain.modals.HomeCategory

data class HomeDto(
    val category: List<HomeCategory>,
    val banner: List<String>
)