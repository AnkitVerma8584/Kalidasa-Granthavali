package com.kalidasagranthavali.ass.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kalidasagranthavali.ass.R
import com.kalidasagranthavali.ass.presentation.theme.KalidasaGranthavaliTheme
import com.kalidasagranthavali.ass.presentation.ui.navigation.modal.NavigationFragment
import com.kalidasagranthavali.ass.util.locale.LocalHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalidasaGranthavaliTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainPage(onLanguageSelected = {
                        LocalHelper.setLocale(this@MainActivity, it)
                        recreate()
                    })
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocalHelper.onAttach(newBase!!))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainPage(
    onLanguageSelected: (tag: String) -> Unit,
    allScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support,
        NavigationFragment.SubCategory,
        NavigationFragment.SubToSubCategory,
        NavigationFragment.Files,
        NavigationFragment.FileDetails
    ),
    menuScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support
    )
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            allScreens.find { it.route == navController.currentBackStackEntry?.destination?.route }
        }
    }

    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = currentFragment?.icon != null,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.menu),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(8.dp))
                menuScreens.forEach { item ->
                    MenuItem(item = item,
                        isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onMenuClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        })
                }
            }
        }) {

        val scaffoldState = rememberScaffoldState()
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            AppBar(
                title = currentFragment?.title?.asString()
                    ?: stringResource(id = R.string.app_name),
                hamburgerIconClicked = { scope.launch { drawerState.open() } },
                navigationBackClicked = { navController.navigateUp() },
                isNavigationFragment = currentFragment?.icon != null,
                onLanguageSelected = onLanguageSelected
            )
        }) {
            NavHostFragments(navController = navController, paddingValues = it, scaffoldState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItem(
    item: NavigationFragment, isSelected: Boolean, onMenuClick: (item: NavigationFragment) -> Unit
) {
    NavigationDrawerItem(icon = {
        item.icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }, label = {
        Text(
            item.title.asString(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge
        )
    }, selected = isSelected, onClick = {
        onMenuClick(item)
    }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    hamburgerIconClicked: () -> Unit,
    navigationBackClicked: () -> Unit,
    isNavigationFragment: Boolean,
    onLanguageSelected: (tag: String) -> Unit
) {
    TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, navigationIcon = {
        if (isNavigationFragment) {
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { hamburgerIconClicked() }
                    .padding(8.dp))
        } else {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navigationBackClicked() }
                    .padding(8.dp))
        }
    }, actions = {
        TopAppBarDropdownMenu(onLanguageSelected)
    })
}

@Composable
private fun TopAppBarDropdownMenu(
    onLanguageSelected: (tag: String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                painterResource(id = R.drawable.ic_language), contentDescription = "Change Language"
            )
        }
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        MenuItem(R.string.en) {
            onLanguageSelected("en")
        }
        MenuItem(R.string.hi) {
            onLanguageSelected("hi")
        }
        MenuItem(R.string.kn) {
            onLanguageSelected("kn")
        }
        MenuItem(R.string.sa) {
            onLanguageSelected("sa")
        }
    }
}


@Composable
private fun MenuItem(
    @StringRes languageId: Int,
    onMenuClick: () -> Unit
) {
    DropdownMenuItem(text = {
        Text(
            text = stringResource(id = languageId),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, onClick = {
        onMenuClick()
    })
}
