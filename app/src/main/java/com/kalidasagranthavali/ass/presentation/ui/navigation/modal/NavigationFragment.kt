package com.kalidasagranthavali.ass.presentation.ui.navigation.modal

import androidx.annotation.DrawableRes
import com.kalidasagranthavali.ass.R


sealed class NavigationFragment(
    val route: String,
    var title: String,
    @DrawableRes val icon: Int? = null
) {
    object Home : NavigationFragment(
        route = "home", title = "Home", icon = R.drawable.ic_home
    )

    object About : NavigationFragment(
        route = "about", title = "About Us", icon = R.drawable.ic_about
    )

    object Contact : NavigationFragment(
        route = "contact", title = "Contact Us", icon = R.drawable.ic_contact
    )

    object Support : NavigationFragment(
        route = "support", title = "Support", icon = R.drawable.ic_support
    )

    object SubCategory : NavigationFragment(
        route = "sub_category/{cat_id}", title = "Sub Category"
    )

    object Files : NavigationFragment(
        route = "files/{cat_id}/{sub_cat_id}", title = "Files"
    )

    object FileDetails : NavigationFragment(
        route = "file_details/{file_id}", title = "File Details"
    )

}

