package com.example.zenpath.ui.filtersearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.zenpath.R

@Composable
fun FilterSearchScreen() {
    val protestStrike = FontFamily(
        Font(R.font.protest_strike, FontWeight.Light),
    )

    // This Box is the single container for everything
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 18.dp , vertical = 38.dp)
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
                .align(Alignment.TopEnd) // Aligns this box to the top-right of its parent
                .padding(horizontal = 18.dp, vertical = 38.dp) // Apply padding to the box itself
                .size(45.dp)
                .offset(x = 10.dp,y = -45.dp)
                .clickable { /* Handle click event */ }
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Back Arrow",
                modifier = Modifier.size(16.dp),
                contentScale = ContentScale.Fit
            )
        }
        // Place other UI elements here

        Spacer(modifier = Modifier.height(16.dp))

        // Column for the rest of the UI content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal   = 10.dp , vertical = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Your other UI elements go here, e.g., Text, other Composables
            // For example:
            Text(
                text = "Filter by\nCategories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = protestStrike,
                color = colorResource(id = R.color.black)
            )


            Text(
                text = "How have you been feeling \n" +
                        "lately ?",
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

            Spacer(modifier = Modifier.height(16.dp))

            MoodSelectorLayout()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "What's your main goal?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 18.dp),
                fontFamily = FontFamily.Serif,
                color = colorResource(id = R.color.black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GoalButton("Lose Weight")
                GoalButton("Reduce Stress")
                GoalButton("Improve Sleep")
                GoalButton("Gain Confidence")
                GoalButton("Save Changes")
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
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// All MoodSelector related composables are now here
// All MoodSelector related composables are now here
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSelector() {
    val moods = listOf("Stress", "Dissapoint", "Confused", "Sleep")
    var selectedMoodIndex by remember { mutableIntStateOf(2) }
    var sliderPosition by remember { mutableStateOf(0.5f) }

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
                thumbColor = colorResource(id = R.color.blue),
                activeTrackColor = colorResource(id = R.color.light_blue),
                inactiveTrackColor = colorResource(id = R.color.light_blue),
            ),
            thumb = {
                // Custom thumb composable
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(colorResource(id = R.color.blue), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.slide_arrow),
                        contentDescription = "Slider Arrows",
                        modifier = Modifier.size(16.dp)
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
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        options.forEachIndexed { index, (label, emoji) ->
            MoodButtonNew(
                label = label,
                emojiResourceId = emoji,
                isSelected = index == selectedIndex,
                onClick = { selectedIndex = index }
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
    val emojiSize = if (isSelected) 65.dp else 60.dp

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
                modifier = Modifier.size(40.dp)
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

@Preview(showBackground = true)
@Composable
fun FilterSearchScreenPreview() {
    FilterSearchScreen()
}