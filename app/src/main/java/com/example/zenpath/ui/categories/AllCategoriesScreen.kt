package com.example.zenpath.ui.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 22  .dp, vertical = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                    .padding(start = 8.dp, end = 8.dp) // Apply outer horizontal padding
            ) {
                // Row with TextField and Search Icon
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(), // This now fills the available width inside the Column
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // TextField Box with weight to occupy all remaining space
                    // Loading Text
                    Text(
                        text = "Filter by\ncategories",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontFamily = protestStrike,
                        modifier = Modifier.padding(top = 16.dp)
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.width(24.dp)) // spacing between TextField and icon

                    // Settings Icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .offset(y = (-5).dp)
                            .padding(bottom = 2.dp)
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllCategoriesScreenPreview() {
    AllCategoriesScreen(navController = rememberNavController())
}