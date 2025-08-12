package com.example.zenpath.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.R
import com.example.zenpath.ui.navigation.AppNavHost
import com.example.zenpath.ui.theme.ZenpathTheme
import com.example.zenpath.utils.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Zenpath)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenpathTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
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
