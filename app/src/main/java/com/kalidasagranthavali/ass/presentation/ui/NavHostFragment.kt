package com.kalidasagranthavali.ass.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.presentation.ui.navigation.modal.NavigationFragment
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.about.AboutPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.CategoryPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.contact.ContactPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.FileDetailsPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.FilePage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category.SubCategoryPage
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.support.SupportPage

@Composable
fun NavHostFragments(
    navController: NavHostController, paddingValues: PaddingValues, scaffoldState: ScaffoldState
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        navController = navController,
        startDestination = NavigationFragment.Home.route
    ) {
        composable(route = NavigationFragment.Home.route) {
            CategoryPage(scaffoldState = scaffoldState) {
                NavigationFragment.SubCategory.title = StringUtil.DynamicText(it.name)
                navController.navigate("sub_category/${it.id}") {
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
            SubCategoryPage(onNavigateToFile = {
                NavigationFragment.Files.title = StringUtil.DynamicText(it.name)
                navController.navigate("files/${it.cat_id}/${it.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable(
            route = NavigationFragment.Files.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                navArgument("sub_cat_id") { type = NavType.IntType })
        ) {
            FilePage(onFileClicked = {
                NavigationFragment.FileDetails.title = StringUtil.DynamicText(it.name)
                navController.navigate("file_details/${it.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable(route = NavigationFragment.FileDetails.route,
            arguments = listOf(navArgument("file_id") { type = NavType.IntType })) {
            FileDetailsPage()
        }
    }
}