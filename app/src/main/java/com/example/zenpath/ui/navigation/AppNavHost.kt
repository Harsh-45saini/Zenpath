package com.example.zenpath.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zenpath.ui.splash.SplashScreen
import com.example.zenpath.AuthScreen
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.ui.categories.AllCategoriesScreen
import com.example.zenpath.ui.home.HomeScreen
import com.example.zenpath.ui.listening.ListeningScreen
import com.example.zenpath.ui.mostpopular.MostPopularScreen
import com.example.zenpath.ui.profile.ProfileScreen
import com.example.zenpath.ui.search.SearchScreen
import com.example.zenpath.ui.settings.SettingScreen
import com.example.zenpath.ui.viewmodel.CategoriesViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object MostPopularScreen : Screen("most_popular")
    object Categories : Screen("categories")
    object Settings : Screen("settingsDetail")
    object Profile : Screen("profile_screen")
    object Search : Screen("search_screen")
    object Listening : Screen("listening_screen")
}

fun NavHostController.safeNavigate(
    route: String,
    clearBackStackUpTo: String? = null,
    inclusive: Boolean = false
) {
    this.navigate(route) {
        launchSingleTop = true
        clearBackStackUpTo?.let {
            popUpTo(it) { this.inclusive = inclusive }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            AuthScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.MostPopularScreen.route) {
            MostPopularScreen(navController = navController)
        }

        composable(Screen.Categories.route) {
            val context = LocalContext.current
            val viewModel: CategoriesViewModel = viewModel()
            val prefManager = remember { PrefManager(context) }

            AllCategoriesScreen(
                navController = navController,
                viewModel = viewModel,
                prefManager = prefManager
            )
        }

        composable(Screen.Listening.route) {
            ListeningScreen(
                onBack = { navController.popBackStack() },
                onSearch = { navController.navigate(Screen.Search.route) },
                onPlayPause = { /* Handle play/pause */ }
            )
        }
        composable(Screen.Settings.route) {
            SettingScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToOther = {},
                navController = navController
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}