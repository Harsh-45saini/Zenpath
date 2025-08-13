package com.example.zenpath.ui.categories

import android.util.Log
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zenpath.R
import com.example.zenpath.data.local.PrefManager

import com.example.zenpath.ui.viewmodel.CategoriesViewModel

@Composable
fun AllCategoriesScreen(
    navController: NavController,
    viewModel: CategoriesViewModel,
    prefManager: PrefManager
) {
    val categoriesResponse by viewModel.categoriesResponse.observeAsState()
    val error by viewModel.error.observeAsState()
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
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
            .padding(horizontal = 22.dp, vertical = 40.dp)
    ) {
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
                            modifier = Modifier.width(80.dp).wrapContentHeight()
                        )
                    }
                }
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            else -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                model = "https://your_base_url.com$iconUrl", // prepend base URL if iconUrl is relative
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