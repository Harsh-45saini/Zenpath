package com.example.zenpath.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.zenpath.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blue))
            .padding(horizontal = 18.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Top Row: Cancel icon and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFD0E8FF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftarrow),
                        contentDescription = "Cancel Icon",
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Privacy Policy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Body Text
            val paragraphText = "This is where your privacy policy content goes. You can explain data usage, retention, user rights, and more."

            repeat(4) {
                Text(
                    text = paragraphText,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = sansFont,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NotificationButton(
                text = "Decline",
                backgroundColor = Color.Transparent,
                textColor = Color.White,
                borderColor = Color.Black
            )
            NotificationButton(
                text = "Accept",
                backgroundColor = Color.White,
                textColor = colorResource(id = R.color.blue)
            )
        }
    }
}

@Composable
fun NotificationButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color? = null
) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .then(
                if (borderColor != null) Modifier.border(1.dp, borderColor, RoundedCornerShape(12.dp))
                else Modifier
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontFamily = sansFont,
            fontWeight = FontWeight.SemiBold
        )
    }
}