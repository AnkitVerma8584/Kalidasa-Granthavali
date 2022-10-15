package com.kalidasagranthavali.ass.util

import android.util.Log

fun Any?.print(tag: String = "TAG") {
    Log.e(tag, this.toString())
}

fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf") && !this.endsWith(".txt")

fun String.isReadable(): Boolean = this.endsWith(".txt")