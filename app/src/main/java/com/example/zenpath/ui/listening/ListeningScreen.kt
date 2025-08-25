package com.example.zenpath.ui.listening

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onBack() }
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

                // Center Title + Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f) // 🔹 takes remaining space to stay centered
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.audiobook), // replace with your icon
                        contentDescription = "Music Icon",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 10.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Listening",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.blue)
                    )
                }

                // Search Button
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onSearch() }
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left label
                Text("01:42", color = Color.Black, fontSize = 12.sp)

                // 🔹 Fake Waveform in the middle
                Row(
                    modifier = Modifier
                        .weight(1f) // take available space between labels
                        .padding(horizontal = 8.dp), // spacing from labels
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

                // Right label
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // spread evenly across row
                ) {
                    // 🔹 Shuffle
                    IconButton(onClick = { /* shuffle */ }) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // 🔹 Center Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
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
                            Image(
                                painter = painterResource(id = R.drawable.btn_play), // 🔹 your custom drawable
                                contentDescription = "Play",
                                modifier = Modifier.size(20.dp)
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

                    // 🔹 Replay
                    IconButton(onClick = { /* replay */ }) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "Replay",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
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