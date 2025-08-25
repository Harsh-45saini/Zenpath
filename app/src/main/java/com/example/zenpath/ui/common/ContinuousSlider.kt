package com.example.yourapp.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import com.example.zenpath.R

@Composable
fun ContinuousSlidingText(
    text: String,
    textColor: Color = Color.White,
    fontSize: Int = 16,
    durationMillis: Int = 6000
) {
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteSlide")
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = screenWidth.value,
        targetValue = -screenWidth.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "slide"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clipToBounds(),
        contentAlignment = Alignment.CenterStart
    ) {
        androidx.compose.material3.Text(
            text = text,
            color = textColor,
            fontFamily = ptSerifFont,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            modifier = Modifier.graphicsLayer {
                translationX = animatedOffset
            }
        )
    }
}