package com.kalidasagranthavali.ass.data.remote

object Api {

    const val BASE_URL = "http://muktipatha.in/kalidasa/"
    const val GET_BANNER = "app/banner.php"
    const val GET_CATEGORY = "app/category.php"
    const val GET_SUBCATEGORIES = "app/sub_category.php"
    const val GET_FILES = "app/files.php"

    fun String.getUrl(): String = BASE_URL + this
}