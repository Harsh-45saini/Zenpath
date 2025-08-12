package com.example.zenpath.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zenpath.SplashScreen
import com.example.zenpath.AuthScreen
import com.example.zenpath.ui.categories.AllCategoriesScreen
import com.example.zenpath.ui.home.HomeScreen
import com.example.zenpath.ui.mostpopular.MostPopularScreen
import com.example.zenpath.ui.profile.ProfileScreen
import com.example.zenpath.ui.search.SearchScreen
import com.example.zenpath.ui.settings.SettingScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            AuthScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("most_popular") {
            MostPopularScreen(navController = navController)
        }

        composable("categories") {
            AllCategoriesScreen(navController = navController)
        }

        composable("settingsDetail") {
            SettingScreen(navController = navController)
        }

        composable("profile_screen") {
            ProfileScreen(
                onNavigateToOther = {},
                navController = navController
            )
        }

        composable("search_screen") {
            SearchScreen(navController = navController)
        }
    }
}