package com.example.zenpath.ui.dailypractice

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.zenpath.R
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.repository.DailyPracticeRepository
import com.example.zenpath.ui.viewmodel.CategoryUiState
import com.example.zenpath.ui.viewmodel.DailyPracticeUiState
import com.example.zenpath.ui.viewmodel.DailyPracticeViewModel
import com.example.zenpath.ui.viewmodel.DailyPracticeViewModelFactory

@Composable
fun DailyPracticeScreen(
    navController: NavController,
    categoryId: Int,
    viewModel: DailyPracticeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = DailyPracticeViewModelFactory(
            DailyPracticeRepository(
                ApiClient.apiService
            )
        )
    )
) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    val uiState by viewModel.uiState.collectAsState()
    val categoryState by viewModel.categoryState.collectAsState()
    val token = prefManager.getToken()

    // 🔹 Trigger API call when screen opens
    LaunchedEffect(categoryId) {
        viewModel.fetchCategoryDetails(token , categoryId)
        viewModel.fetchDailyPractices(token , categoryId)
    }

    val dailyPractices = when (uiState) {
        is DailyPracticeUiState.Success -> (uiState as DailyPracticeUiState.Success).data
        else -> emptyList()
    }
    val protestStrike = FontFamily(Font(R.font.protest_strike, FontWeight.Light))
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val ptSans = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        // 🔹 Top Bar
        TopBar(navController, ptSerifFont,categoryState)

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Info Card
        InfoCard()

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Section Title
        Text(
            text = "Daily practices",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp,
            fontFamily = protestStrike,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(12.dp))

//        // 🔹 Daily Practice Grid (1 column = vertical list)
//        val dailyPractices = listOf(
//            "Morning Meditation" to "Start your day calm",
//            "Gratitude Writing" to "Write 3 things daily",
//            "Mindful Breathing" to "Practice 5 mins",
//            "Evening Reflection" to "Review your day"
//        )

// 🔹 Daily Practices Section
        when (uiState) {
            is DailyPracticeUiState.Loading -> {
                Text(
                    "Loading practices...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }

            is DailyPracticeUiState.Error -> {
                Text(
                    "Error: ${(uiState as DailyPracticeUiState.Error).message}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            is DailyPracticeUiState.Success -> {
                val dailyPractices = (uiState as DailyPracticeUiState.Success).data

                if (dailyPractices.isEmpty()) {
                    Text(
                        "No practices found",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(dailyPractices) { practice ->
                            val imageUrl = ApiClient.BASE_URL + practice.cover_image
                            Log.d("DEBUG DailyPractice","DEBUG DailyPractice image: $imageUrl")

                            DailyPracticeItem(
                                imageUrl = imageUrl,
                                title = practice.title,
                                subtitle = practice.description
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(12.dp)) // border radius
//                .background(colorResource(id = R.color.blue)) // semi-transparent bg
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            Text(
//                text = "Load More",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontFamily = ptSans,
//                fontWeight = FontWeight.Bold
//            )
//        }
    }
}

@Composable
fun InfoCard() {
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))

    Box(
        modifier = Modifier
            .fillMaxWidth() // takes full width
            .height(150.dp) // fixed height
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = R.color.light_blue))
            .padding(26.dp) // inner padding
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔹 Static Image on the left
            Image(
                painter = painterResource(id = R.drawable.illustration1),
                contentDescription = "Card Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.4f) // 40% width
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 🔹 Text Column on the right
            Column(
                modifier = Modifier
                    .weight(0.6f) // 60% width
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Meditative Insights",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp,
                    fontFamily = ptSerifFont,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "involves focusing on something intently as a way of staying.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.blue),
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun DailyPracticeItem(
    imageUrl: String?, // For remote image
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, end = 6.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(colorResource(id = R.color.light_blue))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔹 Image Box
        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (!imageUrl.isNullOrEmpty()) {
                // ✅ Load from URL with Coil
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Practice Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(R.drawable.rectangle_9500),
                    error = painterResource(R.drawable.rectangle_9500)
                )
            } else {
                // 🔹 Fallback if no URL
                Image(
                    painter = painterResource(id = R.drawable.rectangle_9500),
                    contentDescription = "Practice Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 🔹 Play button overlay
            Image(
                painter = painterResource(id = R.drawable.play_icon),
                contentDescription = "Play Button",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 🔹 Text Column
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                color = colorResource(id = R.color.blue),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TopBar(
    navController: NavController,
    ptSerifFont: FontFamily,
    categoryState: CategoryUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 🔹 Back button
        Box(
            modifier = Modifier
                .size(45.dp)
                .clickable { navController.popBackStack("home", false) }
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

        // 🔹 Title (category)
        when (categoryState) {
            is CategoryUiState.Loading -> {
                Text(
                    "Loading...",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            is CategoryUiState.Error -> {
                Text(
                    "Error: ${categoryState.message}",
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            is CategoryUiState.Success -> {
                val category = categoryState.category
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f) // ✅ only takes middle space
                ) {
                        AsyncImage(
                            model = ApiClient.BASE_URL + category.icon,
                            contentDescription = "Category Icon",
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 6.dp)
                        )
                    Text(
                        text = category.name,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.blue),
                        fontFamily = ptSerifFont
                    )
                }
            }
        }

        // 🔹 Search button
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DailyPracticeScreenPreview() {
    val navController = rememberNavController()
    DailyPracticeScreen(navController = navController, categoryId = 1)
}