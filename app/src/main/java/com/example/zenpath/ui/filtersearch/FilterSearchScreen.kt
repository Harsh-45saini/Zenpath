package com.example.zenpath.ui.filtersearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import kotlin.math.roundToInt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.R

@Composable
fun FilterSearchScreen(navController : NavController) {
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    // This Box is the single container for everything
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 38.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // The background image fills the whole screen, without padding
            Image(
                painter = painterResource(id = R.drawable.bg_filtersearch),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds// Use Crop to ensure it fills the space
            )

            // Your back arrow Box placed directly inside the main Box
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(48.dp)
                    .offset(x = (-8).dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        navController.navigate(navController.popBackStack())
                    }
                    .background(Color(0xFFD0E8FF)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Settings Icon",
                    modifier = Modifier.size(25.dp)
                )
            }
            // Place other UI elements here

            Spacer(modifier = Modifier.height(16.dp))

            // Column for the rest of the UI content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                // Your other UI elements go here, e.g., Text, other Composables
                // For example:
                Text(
                    text = "Filter by\nSearch",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(top = 30.dp),
                    fontFamily = protestStrike,
                    color = colorResource(id = R.color.black)
                )


                Text(
                    text = "How have you been feeling lately ?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 18.dp),
                    fontFamily = FontFamily.Serif,
                    color = colorResource(id = R.color.black)
                )
                // Mood Selector UI added here
                MoodSelector()

                Text(
                    text = "I've recognize my emotions as I \n" +
                            "experience them",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 18.dp),
                    fontFamily = FontFamily.Serif,
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(26.dp))

                MoodSelectorLayout()

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text = "What's your main goal?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(26.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GoalButton("Stress Decrease")
                    GoalButton("Happiness")
                    GoalButton("Sleep Better")
                    GoalButton("Positivity")
                    GoalButton("Daily Inspiration")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Select Duration",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 30.dp),
                    fontFamily = protestStrike,
                    color = colorResource(id = R.color.black)
                )

                DurationSelector()

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        ActionButton("Apply Search", R.drawable.search) {
                            // Handle Apply click
                        }

                        ActionButton("Cancel All Filter", R.drawable.filter) {
                            // Handle Cancel click
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DurationSelector() {
    val durations = listOf(3, 5, 10, 20, 30, 40, 60) // in minutes
    var sliderPosition by remember { mutableFloatStateOf(3f) }

    // Find closest duration
    val closestDuration = durations.minByOrNull { kotlin.math.abs(it - sliderPosition.roundToInt()) } ?: 20

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Slider with speech bubble
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Speech bubble above thumb
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(y = (-30).dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.blue))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "$closestDuration min",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            // Slider
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 3f..60f,
                steps = durations.size - 2,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(id = R.color.blue),
                    activeTrackColor = colorResource(id = R.color.light_blue),
                    inactiveTrackColor = colorResource(id = R.color.light_blue),
                ),
                thumb = {
                    // Custom thumb with arrows
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(colorResource(id = R.color.blue), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.slide_arrow),
                            contentDescription = "Slider Thumb",
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Labels Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            durations.forEach { minute ->
                Text(
                    text = if (minute < 60) "${minute.toString().padStart(2, '0')} min" else "1 hour",
                    fontSize = 12.sp,
                    color = if (minute == closestDuration) colorResource(id = R.color.blue) else Color.Gray
                )
            }
        }
    }
}

@Composable
fun GoalButton(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = colorResource(id = R.color.blue),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// All MoodSelector related composable are now here
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSelector() {
    val moods = listOf("Stress", "Dissapoint", "Confused", "Sleep")
    var selectedMoodIndex by remember { mutableIntStateOf(2) }
    var sliderPosition by remember { mutableFloatStateOf(0.5f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp) // Added padding to separate from text
    ) {
        // Mood Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            moods.forEachIndexed { index, mood ->
                MoodButton(
                    mood = mood,
                    isSelected = index == selectedMoodIndex,
                    onMoodSelected = { selectedMoodIndex = index }
                )
            }
        }

        // Slider
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = colorResource(id = R.color.light_blue),
                inactiveTrackColor = colorResource(id = R.color.light_blue),
            ),
            thumb = {
                // Custom thumb composable
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp , colorResource(id = R.color.blue), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.slide_arrow),
                        contentDescription = "Slider Arrows",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        )
    }
}

@Composable
fun MoodButton(mood: String, isSelected: Boolean, onMoodSelected: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onMoodSelected(mood) }
    ) {
        if (isSelected) {
            // Speech Bubble
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.blue))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = mood, color = Color.White, fontSize = 14.sp)
                }
                // Pointer of the speech bubble
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(6.dp)
                        .background(colorResource(id = R.color.blue))
                        .clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50))
                )
            }
        }

        // Emoji Container
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(colorResource(id = R.color.light_blue), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = getEmojiResource(mood)),
                contentDescription = mood,
                modifier = Modifier.size(36.dp)
            )
        }
        Text(text = mood, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}

fun getEmojiResource(mood: String): Int {
    return when (mood) {
        "Stress" -> R.drawable.disappointed
        "Dissapoint" -> R.drawable.relieved
        "Confused" -> R.drawable.disappointed
        "Sleep" -> R.drawable.relieved
        else -> 0
    }
}

// Below are the new composables to add
//---------------------------------------------------------

@Composable
fun MoodSelectorLayout() {
    var selectedIndex by remember { mutableIntStateOf(2) } // 'Often' is selected by default

    val options = listOf(
        "Rarely" to R.drawable.disappointed,
        "Sometimes" to R.drawable.stress_relief,
        "Often" to R.drawable.relieved,
        "Always" to R.drawable.disappointed
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        options.forEachIndexed { index, (label, emoji) ->
            MoodButtonNew(
                label = label,
                emojiResourceId = emoji,
                isSelected = index == selectedIndex,
                onClick = { selectedIndex = index },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MoodButtonNew(
    modifier: Modifier = Modifier,
    label: String,
    emojiResourceId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) colorResource(id = R.color.light_blue) else colorResource(id = R.color.light_blue)
    val contentColor = if (isSelected) Color.White else Color.Black
    val emojiBgColor = if (isSelected) colorResource(id = R.color.blue) else colorResource(id = R.color.light_blue)
    val emojiSize = if (isSelected) 45.dp else 40.dp

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(emojiSize)
                .clip(RoundedCornerShape(10.dp))
                .background(emojiBgColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = emojiResourceId),
                contentDescription = label,
                modifier = Modifier
                    .fillMaxSize()   // make it scale with Box
                    .padding(6.dp),  // keep some margin so it doesn’t stretch edge-to-edge
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ActionButton(text: String, image : Int,onClick: () -> Unit ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.blue))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = image), // replace with your icon
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp)) // space between icon and text

            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSearchScreenPreview() {
    val navController = rememberNavController()
    FilterSearchScreen(navController = navController)
}