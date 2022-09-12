package com.kalidasagranthavali.ass.data.local.mapper

import com.kalidasagranthavali.ass.data.local.modals.Banner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<Banner>>.mapToStringList(): Flow<List<String>> = this.map {
    it.map { b ->
        b.image
    }
}

fun List<String>.mapToBannerList(): List<Banner> = this.map {
    Banner(it)
}