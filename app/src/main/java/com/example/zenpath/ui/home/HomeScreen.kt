package com.example.zenpath.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import coil.compose.AsyncImage
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.layout.*
import com.airbnb.lottie.compose.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.airbnb.lottie.LottieProperty
import kotlinx.coroutines.launch
import com.example.zenpath.R
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.ui.theme.ZenpathTheme
import com.example.zenpath.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import com.example.zenpath.data.model.Category
import com.example.zenpath.data.model.Practice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    val userName by viewModel.firstName.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val ptSansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val categories by viewModel.categories.collectAsState()
//    val token = prefManager.getToken()
    val dashboardData by viewModel.dashboardData.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.navigateTo.collect { route ->
                navController.navigate(route)
            }
        }

        // This code will now run as expected
        delay(300)
        viewModel.loadFirstName(prefManager)
        viewModel.loadDashboard(prefManager)
    }

    if (!isConnected) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            LottieLoader()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 40.dp)
        ) {
            TopProfileBar(userName,navController = navController)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "How are you feeling today ?",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 20.sp,
                    fontFamily = ptSansFont
                ),
                color = colorResource(id = R.color.blue)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.height(100.dp)) {
                if (categories.isNotEmpty()) {
                    FourDiffBox(categories = categories)
                } else {
                    Text("Loading...")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Latest Update",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.blue),
                    fontFamily = ptSerifFont
                )

                Text(
                    text = "See all",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.blue),
                    fontFamily = ptSansFont
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            dashboardData?.latestPractice?.let { practice ->
                InfoCard(practice = practice)
            }

            Spacer(modifier = Modifier.height(20.dp))
            TwoColumnLayout(viewModel = viewModel)
        }
    }
}

@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    animationFile: String = "lottie/dotted_loader.json"
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(animationFile)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = Color.Blue.toArgb(), // Blue color
            "**"// Apply to all layers
        )
    )

    LottieAnimation(
        composition = composition,
        progress = {progress},
        modifier = modifier
            .size(120.dp),
        dynamicProperties = dynamicProperties
    )
}

@Composable
fun TwoColumnLayout(viewModel: HomeViewModel) {
    val dashboardData by viewModel.dashboardData.collectAsState()
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val ptSansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))

    val topPractices = dashboardData?.topPractices.orEmpty()

    // Fallback if less than 2 practices are available
    val practice1 = topPractices.getOrNull(0)
    val practice2 = topPractices.getOrNull(1)

    Row(modifier = Modifier.fillMaxWidth()) {

        // ---------- LEFT COLUMN ----------
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = "Most",
                fontSize = 28.sp,
                fontFamily = ptSerifFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.blue)
            )

            Text(
                text = "Popular",
                fontSize = 28.sp,
                fontFamily = ptSerifFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.blue)
            )

            Spacer(modifier = Modifier.height(8.dp))

            practice1?.let {
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(22.dp))
                ) {
                    AsyncImage(
                        model = ApiClient.BASE_URL + it.coverImage,
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Image(
                        painter = painterResource(id = R.drawable.play_icon),
                        contentDescription = "Play",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    )

                    Text(
                        text = it.title,
                        color = Color.White,
                        fontFamily = ptSerifFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(14.dp)
                    )
                }
            }
        }

        // ---------- RIGHT COLUMN ----------
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            practice2?.let {
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(22.dp))
                ) {
                    AsyncImage(
                        model = ApiClient.BASE_URL + it.coverImage,
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Image(
                        painter = painterResource(id = R.drawable.play_icon),
                        contentDescription = "Play",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    )

                    Text(
                        text = it.title,
                        color = Color.White,
                        fontFamily = ptSerifFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.onExploreMoreClicked() },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Explore More",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ptSansFont,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun InfoCard(practice: Practice) {
    val ptSerifFont = FontFamily(
        Font(R.font.ptserif_regular, FontWeight.Normal)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = R.color.light_blue))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Column: 60%
            Column(
                modifier = Modifier
                    .weight(0.6f)
            ) {
                Text(
                    text = practice.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 22.sp,
                    fontFamily = ptSerifFont,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.blue)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = practice.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.blue),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* Action */ },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.play_icon_white),
                            contentDescription = "Listen Icon",
                            modifier = Modifier.size(25.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Listen Now",
                            color = colorResource(id = R.color.white)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ApiClient.BASE_URL + practice.coverImage)
                    .crossfade(true)
                    .error(R.drawable.group)
                    .build(),
                contentDescription = "Zen Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun TopProfileBar(userName: String? , navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Side: Avatar + Hi Text
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate("profile_screen") {
                            launchSingleTop = true
                        }
                    }
                    .background(colorResource(id = R.color.light_blue))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.man_face1),
                    contentDescription = "Image of man",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 4.dp, top = 4.dp, end = 4.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hi! ${userName ?: "User"}",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 35.sp,
                    fontFamily = FontFamily.Serif,
                ),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp) // Optional rounded border
                )
                .padding(5.dp), // Padding inside the border
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate("categories")
                    }
                    .background(colorResource(id = R.color.light_blue)),
                contentAlignment = Alignment.Center// dark_green_image22
            ) {
                Image(
                    painter = painterResource(id = R.drawable.category),
                    contentDescription = "Image of man",
                    modifier = Modifier
                        .size(30.dp)
                        .fillMaxSize()
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate("search_screen")
                    }
                    .background(colorResource(id = R.color.blue)),
                contentAlignment = Alignment.Center// dark_green_image22
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Image of man",
                    modifier = Modifier
                        .size(30.dp)
                        .fillMaxSize()
                        .padding(4.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Composable
fun FourDiffBox(categories: List<Category>) {
    val ptSansFont = FontFamily(
        Font(R.font.ptsans_regular, FontWeight.Normal)
    )

    Log.d("FourDiffBox", "Received categories: ${categories.size}")

    val displayedCategories = categories.take(4)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        displayedCategories.forEach { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(70.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(id = R.color.light_blue))
                        .clickable { },
                    contentAlignment = Alignment.Center
                )   {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(ApiClient.BASE_URL + category.icon)
                                .crossfade(true)
                                .error(R.drawable.group) // 🔁 fallback when loading fails (e.g., no internet)
                                .build()
                        ),
                        contentDescription = category.name,
                        modifier = Modifier.size(40.dp)
                            .background(Color.Gray),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    text = category.name,
                    fontSize = 11.sp,
                    color = colorResource(id = R.color.blue),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    fontFamily = ptSansFont,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ZenpathTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}