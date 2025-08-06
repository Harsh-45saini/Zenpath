package com.example.zenpath

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.ui.home.HomeScreen
import com.example.zenpath.ui.mostpopular.MostPopularScreen
import com.example.zenpath.ui.profile.ProfileScreen

import com.example.zenpath.ui.settings.PrivacyPolicyScreen
import com.example.zenpath.ui.settings.SettingScreen
import com.example.zenpath.utils.BaseActivity
import com.example.zenpath.ui.theme.ZenpathTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Zenpath)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenpathTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "splash") {
                    composable("splash") { SplashScreen(navController) }
                    composable("login") { AuthScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("most_popular") { MostPopularScreen() }
                    composable("privacy_policy") {
                        PrivacyPolicyScreen(navController = navController)
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
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ZenpathTheme {
//        Greeting("Android")
//    }
