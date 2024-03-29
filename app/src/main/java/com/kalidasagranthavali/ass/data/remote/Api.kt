package com.kalidasagranthavali.ass.data.remote

object Api {

    const val BASE_URL = "http://muktipatha.in/kalidasa/"

    fun String.getDocumentExtension(): String = "uploads/documents/$this"

    fun String.getDocumentUrl():String = BASE_URL + this.getDocumentExtension()

    const val GET_BANNER = "app/banner.php"
    const val GET_CATEGORY = "app/category.php"
    const val GET_SUBCATEGORIES = "app/sub_category.php"
    const val GET_SUB_TO_SUBCATEGORIES = "app/sub_to_sub_category.php"
    const val GET_FILES = "app/files.php"


}