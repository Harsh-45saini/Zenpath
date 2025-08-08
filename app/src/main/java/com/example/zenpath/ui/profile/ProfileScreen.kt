package com.example.zenpath.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.zenpath.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.ui.theme.ZenpathTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.example.zenpath.ui.viewmodel.ProfileViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ProfileScreen(onNavigateToOther: () -> Unit,
                  navController: NavHostController,
                  profileViewModel: ProfileViewModel = viewModel() ) {
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    val userName by profileViewModel.userFullName.observeAsState("")
    val userEmail by profileViewModel.userEmail.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        RowWithImagesAndCenterText(navController)

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // You can change the height as needed
                .background(
                    color = colorResource(id = R.color.blue_shade), // light blue shade
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            RowWithImageAndText(userName = userName, userEmail = userEmail)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Daily Progress",
            fontWeight = FontWeight.Bold,
            fontFamily = protestStrike,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        FourBoxLayout()

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Your Insights",
            fontWeight = FontWeight.Bold,
            fontFamily = protestStrike,
            fontSize = 18.sp
        )

        RowWithTextAndImageBoxes()

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.blue_shade),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chart),
                contentDescription = "Full Box Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(14.dp))
            )
        }
    }
}

@Composable
fun RowWithTextAndImageBoxes() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start Text
        Text(
            text = "This Week:",
            fontSize = 12.sp,
            fontFamily = FontFamily.SansSerif,
            color = colorResource(id = R.color.blue)
        )

        Text(
            text = "05may-11 may",
            fontSize = 12.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue)
        )

        Spacer(modifier = Modifier.weight(1f)) // Pushes the boxes to the end

        // Two image boxes
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = colorResource(R.color.blue), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leftarrow), // Replace with your image
                    contentDescription = "Image 1",
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = colorResource(id = R.color.blue), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leftarrow), // Replace with your image
                    contentDescription = "Image 2",
                    modifier = Modifier.size(24.dp)
                        .graphicsLayer(scaleX = -1f)
                )
            }
        }
    }
}

@Composable
fun RowWithImageAndText(userName: String,
                          userEmail: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box with image on the left
        Box(
            modifier = Modifier
                .size(85.dp)
                .background(
                    color = colorResource(id = R.color.blue), // same light blue
                    shape = RoundedCornerShape(12.dp) // same rounded corners
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.man_face1),
                contentDescription = "Profile",
                modifier = Modifier.size(65.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp)) // Space between image and texts

        // Column with two texts
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName,
                fontSize = 18.sp,
                color = colorResource(id = R.color.blue),
                fontFamily = FontFamily.Serif
            )
            Text(
                text = userEmail,
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
fun RowWithImagesAndCenterText(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start Image Box
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    navController.navigate("home")
                }
                .background(
                    color = colorResource(id = R.color.blue), // light blue
                    shape = RoundedCornerShape(12.dp) // rounded corners
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.leftarrow),
                contentDescription = "Start Image",
                modifier = Modifier.size(25.dp)
            )
        }

        // Center Text
        Text(
            text = "My Profile",
            modifier = Modifier.weight(1f),
            color = colorResource(id = R.color.blue),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif
            )
        )

        // End Image Box
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color(0xFFD0E8FF), // light blue
                    shape = RoundedCornerShape(12.dp) // rounded corners
                )
                .clickable {
                    navController.navigate("settingsDetail")
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "End Image",
                modifier = Modifier.size(25.dp)
            )
        }

    }
}

@Composable
fun FourBoxLayout() {
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Bold),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                imageRes = R.drawable.img_card1,
                timeText = "135h",
                label = "Total meditation",
                protestStrike = protestStrike
            )
            InfoCard(
                imageRes = R.drawable.img_card3,
                timeText = "4h 23m",
                label = "Deep sleep",
                protestStrike = protestStrike
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                imageRes = R.drawable.img_card2,
                timeText = "45 min",
                label = "Longest listening",
                protestStrike = protestStrike
            )
            InfoCard(
                imageRes = R.drawable.img_card2,
                timeText = "2h 5m",
                label = "Light Sleep",
                protestStrike = protestStrike
            )
        }
    }
}

@Composable
fun InfoCard(
    imageRes: Int,
    timeText: String,
    label: String,
    protestStrike: FontFamily
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(
                color = colorResource(id = R.color.blue_shade),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Time text
                Text(
                    text = timeText,
                    fontFamily = protestStrike,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                // Image box at top-end
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.blue),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Image",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            // Label text
            Text(
                text = label,
                color = colorResource(id = R.color.blue),
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val fakeNavController = rememberNavController() // add this
    ZenpathTheme{
        ProfileScreen(
            navController = fakeNavController,
            onNavigateToOther = {}
        )
    }
}
