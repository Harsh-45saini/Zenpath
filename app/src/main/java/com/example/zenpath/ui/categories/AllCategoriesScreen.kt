package com.example.zenpath.ui.categories

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zenpath.R
import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.repository.CategoriesRepository

import com.example.zenpath.ui.viewmodel.CategoriesViewModel
import com.example.zenpath.ui.viewmodel.CategoriesViewModelFactory

@Composable
fun AllCategoriesScreen(
    navController: NavController,
    viewModel: CategoriesViewModel,
    prefManager: PrefManager
) {
    val context = LocalContext.current
    val factory = CategoriesViewModelFactory(CategoriesRepository(), prefManager)
    val categoriesResponse by viewModel.categoriesResponse.observeAsState()
    val error by viewModel.error.observeAsState()
    var localError by remember { mutableStateOf<String?>(null) }
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    LaunchedEffect(viewModel) {
        val token = prefManager.getToken()
        Log.d("TOKEN_CHECK", "Token: $token")
        if (!token.isNullOrBlank()) {
            viewModel.fetchAllCategories(token)  // only call if token exists
        } else {
            localError = "Token missing"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 18.dp , vertical = 38.dp)
    ) {
        // 🔹 Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_settings),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
        )

        // 🔹 Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            // 🔹 Header Row (Text + Back Button)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter by\nCategories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp),
                    fontFamily = protestStrike,
                    color = colorResource(id = R.color.black)
                )

                // Back Button Box
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .offset(x = 15.dp)
                        .clickable { navController.popBackStack() } // or onBack()
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = R.color.light_blue)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.category),
                        contentDescription = "Back Arrow",
                        modifier = Modifier.size(24.dp)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Categories Grid
            when {
                categoriesResponse != null -> {
                    val categories = categoriesResponse?.data ?: emptyList()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)
                    ) {
                        items(categories) { category ->
                            CategoryBox(
                                title = category.name,
                                iconUrl = category.icon,
                                modifier = Modifier
                                    .width(80.dp)
                                    .wrapContentHeight()
                            )
                        }
                    }
                }
                error != null -> {
                    Text("Error: $error", color = Color.Red)
                }
                else -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun CategoryBox(
    title: String,
    iconUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.9f))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
               model = if (iconUrl.startsWith("http")) {
               iconUrl
               } else {
               "${ApiClient.BASE_URL}$iconUrl"
              }
            ,
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.group), // optional placeholder
                error = painterResource(id = R.drawable.group) // optional error icon
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = colorResource(id = R.color.blue),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true, name = "All Categories Screen")
//@Composable
//fun AllCategoriesScreenPreview() {
//    val navController = rememberNavController()
//
//    // Fake PrefManager (won’t really be used in preview)
//    val fakePrefManager = PrefManager(context = androidx.compose.ui.platform.LocalContext.current)
//
//    // Fake ViewModel (empty object just for preview)
//    val fakeViewModel = object : CategoriesViewModel() {}
//
//    AllCategoriesScreen(
//        navController = navController,
//        viewModel = fakeViewModel,
//        prefManager = fakePrefManager
//    )
//}

@Preview(showBackground = true, name = "Single Category Box")
@Composable
fun CategoryBoxPreview() {
    CategoryBox(
        title = "Relaxation",
        iconUrl = "/icons/relax.png"
    )
}