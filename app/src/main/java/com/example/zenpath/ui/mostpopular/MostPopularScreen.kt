package com.example.zenpath.ui.mostpopular

import androidx.compose.material3.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenpath.R
import com.example.zenpath.data.model.MusicItem
import com.example.zenpath.ui.theme.ZenpathTheme

@Composable
fun MostPopularScreen() {
    val ptSerif = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val ptSans = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))

    val sampleMusicList = listOf(
        MusicItem(
            backgroundImage = R.drawable.image1,
            playIcon = R.drawable.play_icon,
            title = "Midnight &\nrelaxation"
        ),
        MusicItem(
            backgroundImage = R.drawable.image2,
            playIcon = R.drawable.play_icon,
            title = "Jogging and \ncycling"
        ),
        MusicItem(
            backgroundImage = R.drawable.image3,
            playIcon = R.drawable.play_icon,
            title = "Midnight\nLaunderetee"
        ),
        MusicItem(
            backgroundImage = R.drawable.image4,
            playIcon = R.drawable.play_icon,
            title = "Jogging and \ncycling"
        ),
        MusicItem(
            backgroundImage = R.drawable.image5,
            playIcon = R.drawable.play_icon,
            title = "Tickle Me \nPink"
        ),
        MusicItem(
            backgroundImage = R.drawable.image6,
            playIcon = R.drawable.play_icon,
            title = "Michael in the\nBathroom"
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 25.dp, vertical = 40.dp)
    ) {
        MostPopular()

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleMusicList) { music ->
                MusicCard(
                    backgroundImage = music.backgroundImage,
                    playIcon = music.playIcon,
                    title = music.title
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Load More",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ptSans,
                color = Color.White,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
fun MostPopular() {
    val ptSerifFont = FontFamily(
        Font(R.font.ptserif_regular, FontWeight.Normal)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 0.dp,
                vertical = 8.dp
            ), // Removed horizontal to avoid duplicate padding
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Start Box - Back Arrow
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = "Back Arrow",
                modifier = Modifier.size(16.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Center Title
        Text(
            text = "Most Popular",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue),
            fontFamily = ptSerifFont
        )

        // End Box - Search Icon
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun MusicCard(
    backgroundImage: Int,
    playIcon: Int,
    title: String
) {
    val ptSerifFont = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))

    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(id = playIcon),
            contentDescription = "Play Button",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .size(28.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontFamily = ptSerifFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MostPopularPreview() {
    ZenpathTheme {
        MostPopularScreen()
    }
}