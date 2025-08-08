package com.example.zenpath.ui.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.R

@Composable
fun AllCategoriesScreen(navController: NavController) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    val categories = listOf(
        "Category 1" to R.drawable.stress_relief,
        "Category 2" to R.drawable.sleeping,
        "Category 3" to R.drawable.disappointed,
        "Category 4" to R.drawable.relieved,
        "Category 5" to R.drawable.frowning,
        "Category 6" to R.drawable.stress_relief,
        "Category 7" to R.drawable.sleeping,
        "Category 8" to R.drawable.disappointed,
        "Category 9" to R.drawable.relieved,
        "Category 10" to R.drawable.frowning,
        "Category 11" to R.drawable.stress_relief,
        "Category 12" to R.drawable.sleeping,
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 22.dp, vertical = 40.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.allcategories_bg),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                // Top Row with title & icon
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filter by\ncategories",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontFamily = protestStrike,
                        modifier = Modifier
                            .padding(top = 16.dp ,start = 10.dp)
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .offset(x = 8.dp, y = (-8).dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(colorResource(id = R.color.blue)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.category),
                            contentDescription = "Settings Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // 12 Boxes in 4 rows × 3 columns
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { (title, imageRes) ->
                        CategoryBox(title = title, imageRes = imageRes)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryBox(
    title: String,
    imageRes: Int, // pass your drawable resource here
    onClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Box(
                modifier = Modifier
                    .size(58.dp) // 48.dp + 10.dp bigger
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.size(30.dp), // slightly smaller than the box
                    contentScale = ContentScale.Fit
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                color = Color.Black,
                fontSize = 10.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
    }
}

@Preview(showBackground = true)
@Composable
fun AllCategoriesScreenPreview() {
    AllCategoriesScreen(navController = rememberNavController())
}