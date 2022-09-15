package com.kalidasagranthavali.ass.util

import android.util.Log

fun Any?.print(tag: String = "TAG") {
    Log.e(tag, this.toString())
}