package com.example.zenpath.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun SearchScreen(navController: NavController) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.search_bg1),
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                    ) {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.leftarrow),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Search Icon",
                                    tint = colorResource(id = R.color.blue)
                                )
                            },
                            placeholder = { Text("Search here...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                cursorColor = Color.Blue
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp)) // spacing between TextField and icon

                    // Settings Icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 2.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD0E8FF))
                            .clickable {
                                navController.navigate("settingsDetail")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.setting),
                            contentDescription = "Settings Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                // Loading Text
                Text(
                    text = "04 items are founded",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = protestStrike,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            }
        }

    }

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val fakeNavController = rememberNavController()
    SearchScreen(navController = fakeNavController)
}