package com.example.zenpath.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
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
                                    modifier = Modifier.size(20.dp),
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
                                cursorColor = Color.Blue,
                                focusedTextColor = Color.Blue,
                                unfocusedTextColor = Color.Blue
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp)) // spacing between TextField and icon

                    // Settings Icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .offset(y = (-6 ).dp)
                            .padding(bottom = 2.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD0E8FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.filter),
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

                CustomVerticalList()

                Spacer(modifier = Modifier.weight(1f))

                CustomRow(navController)

            }
            }
        }

    }

@Composable
fun CustomVerticalList() {
    val items = List(4) { "Item #$it" }

    LazyColumn {
        items(items) { item ->
            ListItem(title = item, subtitle = "This is a subtitle")
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun ListItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp, bottom = 12.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Image inside a Box
        Box(
            modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_9500),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize() // Fill the entire Box
            )
        }
        
        // 2. Space between Box and Column
        Spacer(modifier = Modifier.width(12.dp))

        // 3. Column with two Texts
        Column(
            modifier = Modifier.weight(1f) // 👈 Take available space, but not push arrow away too far
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // 4. Arrow Image (just beside column)
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Arrow Image",
            modifier = Modifier
                .size(18.dp)
        )
    }
}

@Composable
fun ActionBox(
    imageRes: Int,
    backgroundColor: Color = Color(0xFFD0E8FF),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
fun TwoInfoBoxes() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .paint(
                    painter = painterResource(id = R.drawable.rectangle_9513), // replace with your image name
                    contentScale = ContentScale.Crop
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play_icon),
                    contentDescription = "Play",
                    modifier = Modifier
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Midnight\n& relaxation",
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    modifier = Modifier.weight(3f),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .paint(
                    painter = painterResource(id = R.drawable.rectangle_9523), // replace with your image name
                    contentScale = ContentScale.Crop
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play_icon),
                    contentDescription = "Play",
                    modifier = Modifier
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Deep Sleep &\n" +
                            "Refreshment",
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    modifier = Modifier.weight(3f),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun FourActionBoxes(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionBox(
            imageRes = R.drawable.leaf,
            onClick = { navController.navigate("settingsDetail") }
        )
        ActionBox(
            imageRes = R.drawable.moon,
            onClick = { navController.navigate("settingsDetail") }
        )
        ActionBox(
            imageRes = R.drawable.drop,
            onClick = { navController.navigate("settingsDetail") }
        )
        ActionBox(
            imageRes = R.drawable.sun,
            onClick = { navController.navigate("settingsDetail") }
        )
    }
}

@Composable
fun CustomRow(navController: NavController) {
    val protestStrike = FontFamily(Font(R.font.protest_strike, FontWeight.Light))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // First Column
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Last Night Sleep",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = protestStrike
            )

            TwoInfoBoxes()
        }

        Spacer(modifier = Modifier.width(12.dp)) // Spacing between columns

        // Second Column
        FourActionBoxes(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val fakeNavController = rememberNavController()
    SearchScreen(navController = fakeNavController)
}