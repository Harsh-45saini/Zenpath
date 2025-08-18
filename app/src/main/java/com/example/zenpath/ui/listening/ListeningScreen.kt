package com.example.zenpath.ui.listening

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenpath.R

@Composable
fun ListeningScreen(
    onBack: () -> Unit = {},
    onSearch: () -> Unit = {},
    onPlayPause: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🔹 Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_music), // replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black // ✅ Black since background is white
                    )
                }

                Text(
                    text = "Listening",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(onClick = onSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Album Art
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle_9500),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Song Info
            Text(
                text = "Michael in the Bathroom",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "😟 Worried • By Joe Iconis",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Fake Waveform
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(20) { index ->
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(((10..30).random()).dp)
                            .background(
                                if (index < 10) Color.Black else Color.LightGray,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }

            // 🔹 Time Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("01:42", color = Color.Black, fontSize = 12.sp)
                Text("02:30", color = Color.Black, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Bottom Controls
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF325BFF))
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /* prev */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Play / Pause
                    IconButton(
                        onClick = onPlayPause,
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color(0xFF325BFF),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(onClick = { /* next */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Repeat
            IconButton(onClick = { /* repeat */ }) {
                Icon(
                    imageVector = Icons.Default.Repeat,
                    contentDescription = "Repeat",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Listening Screen Preview"
)

fun ListeningScreenPreview() {
    ListeningScreen()
}