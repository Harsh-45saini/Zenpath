package com.example.zenpath.ui.searchFilter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.R

@Composable
fun SearchFilterScreen(navController : NavController) {
    Box(
        modifier = Modifier.fillMaxSize() // Box takes full screen
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_searchfilter), // replace with your bg image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Scales the image to cover entire box
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchFilterPreview() {
    val navController = rememberNavController()
    SearchFilterScreen(navController = navController)
}