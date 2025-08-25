package com.example.zenpath.ui.mostpopular

import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yourapp.ui.common.ContinuousSlidingText
import com.example.zenpath.R
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.ui.navigation.Screen
import com.example.zenpath.ui.theme.ZenpathTheme
import com.example.zenpath.ui.viewmodel.MostPopularViewModel
import com.example.zenpath.ui.viewmodel.MostPopularViewModelFactory

@Composable
fun MostPopularScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: MostPopularViewModel = viewModel(
        factory = MostPopularViewModelFactory(context)
    )

    val musicList by viewModel.musicList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchMostPopularAll()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        MostPopular(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(musicList) { music ->
                MusicCard(
                    imageUrl = music.cover_image,
                    title = music.title
                )
            }
        }
    }
}

@Composable
fun MostPopular(
    navController: NavController
) {
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Start Box - Back Arrow
        Box(
            modifier = Modifier
                .size(45.dp)
                .clickable { navController.popBackStack(   route = Screen.Home.route,
                    inclusive = false)  }
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = "Back Arrow",
                modifier = Modifier.size(16.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Center Title
        Text(
            text = "Most Popular",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue),
            fontFamily = ptSerifFont
        )

        // End Box - Search Icon
        Box(
            modifier = Modifier
                .size(45.dp)
                .clickable { navController.navigate("search_screen") }
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun MusicCard(
    imageUrl: String,
    title: String
) {
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))

    val fullImageUrl = if (imageUrl.startsWith("http")) {
        imageUrl
    } else {
        ApiClient.BASE_URL + imageUrl
    }

    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(fullImageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(id = R.drawable.play_icon),
            contentDescription = "Play Button",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .size(28.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart) // stick to bottom
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            ContinuousSlidingText(
                text = title,
                textColor = Color.White,
                durationMillis = 6000
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MostPopularPreview() {
    ZenpathTheme {
        val fakeNavController = rememberNavController()
        MostPopularScreen(navController = fakeNavController)
    }
}