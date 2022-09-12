package com.kalidasagranthavali.ass.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kalidasagranthavali.ass.presentation.ui.navigation.modal.NavigationFragment
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.about.AboutPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.CategoryPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.contact.ContactPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.file_list.FilePage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category.SubCategoryPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.support.SupportPage

@Composable
fun NavHostFragments(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        navController = navController,
        startDestination = NavigationFragment.Home.route
    ) {
        composable(route = NavigationFragment.Home.route) {
            CategoryPage {
                navController.navigate("sub_category/$it") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = NavigationFragment.About.route) {
            AboutPage()
        }
        composable(route = NavigationFragment.Contact.route) {
            ContactPage()
        }
        composable(route = NavigationFragment.Support.route) {
            SupportPage()
        }
        composable(
            route = NavigationFragment.SubCategory.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType })
        ) {
            SubCategoryPage {
                navController.navigate("files/$it") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = NavigationFragment.Files.route) {
            FilePage()
        }
    }
}