package com.example.zenpath.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.zenpath.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.ui.viewmodel.SettingViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {
    var bottomSheetContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    var name by remember { mutableStateOf("") }
    var tagline by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.75f
    var sheetHeightFraction by remember { mutableStateOf(0.74f) }

    val context = LocalContext.current
    val settingViewModel = remember { SettingViewModel() }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        settingViewModel.fetchPrivacyPolicy()
        settingViewModel.fetchTermsAndConditions()
    }

    val privacyPolicy by settingViewModel.privacyPolicyLiveData.observeAsState("")
    val terms by settingViewModel.termsLiveData.observeAsState("")

    val shape = RoundedCornerShape(
        topStart = 30.dp,
        topEnd = 30.dp,
        bottomStart = 30.dp,
        bottomEnd = 30.dp
    )

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 18.dp, vertical = 40.dp) // Matches ProfileScreen
    ) {
        // Main Box with rounded shape, background, and content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(630.dp)
                .background(color = Color.Transparent, shape = shape)
                .clip(shape)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.bg_settings),
                contentDescription = "Image Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Column with text
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 20.dp, // left
                        top = 35.dp,
                        end = 20.dp,   // right
                        bottom = 20.dp
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Manage your\nSettings",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp) // space between items
                ) {
                    ProfileRowItem(R.drawable.profile, "Account information") {
                        sheetHeightFraction = 0.74f
                        bottomSheetContent = {
                            AccountInfoSheet(
                                onDismiss = { bottomSheetContent = null },
                                name = name,
                                onNameChange = { name = it },
                                tagline = tagline,
                                onTaglineChange = { tagline = it },
                                description = description,
                                onDescriptionChange = { description = it }
                            )
                        }
                    }

                    ProfileRowItem(R.drawable.notification, "Notifications") {
                        sheetHeightFraction = 0.64f
                        bottomSheetContent = {
                            NotificationsSheet(onDismiss = { bottomSheetContent = null })
                        }
                    }

                    ProfileRowItem(R.drawable.calendar, "Daily Reminders") {
                        bottomSheetContent = {
                            DailyReminderSheet(onDismiss = { bottomSheetContent = null })
                        }
                    }

                    ProfileRowItem(R.drawable.profile, "Privacy Policy") {
                        bottomSheetContent = {
                            PrivacyPolicySheet(
                                policyText = privacyPolicy,
                                onDismiss = { bottomSheetContent = null }
                            )
                        }
                     }

                    ProfileRowItem(R.drawable.paper, "Terms of Service") {
                        bottomSheetContent = {
                          TermsAndServicesSheet(
                                termsText = terms,
                                onDismiss = { bottomSheetContent = null }
                            )
                        }
                    }

                    ProfileRowItem(R.drawable.logout, "Sign Out") {
                        showDialog = true
                    }

                    if (showDialog) {
                        CustomSignOutDialog(
                            context = context,
                            settingViewModel = settingViewModel,
                            onDismiss = { showDialog = false},
                            onLogoutRedirect = {
                                // Navigate to Auth screen here
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true } // Clear backstack
                                }
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-6).dp , y = 4.dp)
                    .zIndex(1f)
                    .background(
                        color = Color(0xFFD0E8FF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        navController.navigate("profile_screen") {
                            launchSingleTop = true
                            popUpTo("settingsDetail") { inclusive = true }
                        }
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
    }

    CustomBottomSheetWrapper(
        bottomSheetContent = bottomSheetContent,
        onDismiss = { bottomSheetContent = null },
        sheetHeight = sheetHeight
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetWrapper(
    bottomSheetContent: (@Composable () -> Unit)?,
    onDismiss: () -> Unit,
    sheetHeight: Dp,
    heightFraction: Float = 0.74f
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Only show when content is available
    if (bottomSheetContent != null) {
        LaunchedEffect(Unit) {
            sheetState.show()
        }

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = colorResource(id = R.color.blue_shade),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(heightFraction)
                    .fillMaxWidth()
            ) {
                bottomSheetContent()
            }
        }

    }
}

@Composable
fun AccountInfoSheet(
    onDismiss: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    tagline: String,
    onTaglineChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    val sansFont = FontFamily(
        Font(R.font.ptsans_regular, FontWeight.Light),
    )

    Column(
        modifier = Modifier
            .padding(top = 45.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Row with image boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile Box
            Box(
                modifier = Modifier
                    .width(88.dp)
                    .height(90.dp)
                    .offset(y = 15.dp)
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.man_face1),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 0.dp),
                    contentScale = ContentScale.Crop
                )

                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .padding(end = 5.dp, bottom = 5.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Cancel Box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onDismiss() }
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = "Cancel",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Label + Input Field
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Name",
                fontSize = 14.sp,
                fontFamily = sansFont,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        color = colorResource(id = R.color.light_blue),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 2.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Text(
                text = "Tagline",
                fontSize = 14.sp,
                fontFamily = sansFont,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        color = colorResource(id = R.color.light_blue),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 2.dp)
            ) {
                OutlinedTextField(
                    value = tagline,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Type here...",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = sansFont
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Text(
                text = "Description",
                fontSize = 14.sp,
                fontFamily = sansFont,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.light_blue),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 2.dp)
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = onNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 4,
                    placeholder = {
                        Text(
                            text = "Write here about yourself.....",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = sansFont
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        lineHeight = 20.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

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
                    text = "Save Changes",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = sansFont,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CustomSignOutDialog(
    context: Context,
    settingViewModel: SettingViewModel,
    onDismiss: () -> Unit,
    onLogoutRedirect: () -> Unit
) {
    val ptRegularSerif = FontFamily(
        Font(R.font.ptserif_regular, FontWeight.Light),
    )
    val ptRegularSans = FontFamily(
        Font(R.font.ptsans_regular, FontWeight.Light),
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(colorResource(id = R.color.blue))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon3),
                    contentDescription = "Logout Icon",
                    modifier = Modifier.size(74.dp)
                )

                Text(
                    text = "Logout",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = ptRegularSerif
                )

                Text(
                    text = "Are you really sure that you want to \nlogout now?",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = ptRegularSans
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable {
                            settingViewModel.logout(context) {
                                onLogoutRedirect()  // Go to Auth screen
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Confirm",
                        color = colorResource(id = R.color.blue),
                        fontWeight = FontWeight.Bold,
                        fontFamily = ptRegularSerif
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 0.50.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(colorResource(id = R.color.blue))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Not Now",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ptRegularSerif
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTimePickerDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int, String) -> Unit
) {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    var shift by remember { mutableStateOf("AM") } // AM or PM

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.blue))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Title
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Labels row
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hours", color = Color.White, fontSize = 14.sp)
                    Text("Minutes", color = Color.White, fontSize = 14.sp)
                    Text("Shift", color = Color.White, fontSize = 14.sp)
                }

                // Pickers row
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NumberPicker(
                        value = hours,
                        range = 1..12,
                        onValueChange = { hours = it }
                    )
                    NumberPicker(
                        value = minutes,
                        range = 0..59,
                        onValueChange = { minutes = it }
                    )
                    ShiftPicker(
                        value = shift,
                        onValueChange = { shift = it }
                    )
                }

                // Confirm Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable { onConfirm(hours, minutes, shift) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Confirm",
                        color = colorResource(id = R.color.blue),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Cancel Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 0.25.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(colorResource(id = R.color.blue))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun NumberPicker(value: Int, range: IntRange, onValueChange: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {
            if (value < range.last) onValueChange(value + 1)
        }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color.White)
        }
        Text(value.toString().padStart(2, '0'), color = Color.White, fontSize = 16.sp)
        IconButton(onClick = {
            if (value > range.first) onValueChange(value - 1)
        }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun ShiftPicker(value: String, onValueChange: (String) -> Unit) {
    val options = listOf("AM", "PM")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {
            val currentIndex = options.indexOf(value)
            val newIndex = (currentIndex + 1) % options.size
            onValueChange(options[newIndex])
        }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color.White)
        }
        Text(value, color = Color.White, fontSize = 16.sp)
        IconButton(onClick = {
            val currentIndex = options.indexOf(value)
            val newIndex = (currentIndex - 1 + options.size) % options.size
            onValueChange(options[newIndex])
        }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
        }
    }
}


@Composable
fun NotificationsSheet(onDismiss: () -> Unit) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxHeight() // 👈 Ensure it fills the available bottom sheet height
            .padding(top = 45.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onDismiss() }
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = "Cancel",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "When your are enabled then you will get a push\nnotification. Need help meditation for your\nHealth",
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp),
            fontFamily = sansFont,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue)
        )

        Column(
            modifier = Modifier.padding(top = 15.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            ToggleRow(
                label = "Sleep tips",
                toggleState = notificationsEnabled,
                onToggleChange = { notificationsEnabled = it }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.blue)
            )

            ToggleRow(
                label = "Recommendations",
                toggleState = darkModeEnabled,
                onToggleChange = { darkModeEnabled = it }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.blue)
            )

            ToggleRow(
                label = "Plan push campaigns",
                toggleState = locationEnabled,
                onToggleChange = { locationEnabled = it }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.blue)
            )
        }

        // 👇 This pushes the button to the bottom
        Spacer(modifier = Modifier.weight(1f))

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.notification1),
                    contentDescription = "Save Icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp),
                )

                Text(
                    text = "Disable all Notification",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = sansFont,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PrivacyPolicySheet(
    policyText: String,
    onDismiss: () -> Unit
) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        // Header Row (Heading + Cancel button)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Privacy Policy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = colorResource(id = R.color.blue)
            )

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onDismiss() }
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = "Cancel",
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content Text
        Text(
            text = policyText,
            fontSize = 14.sp,
            fontFamily = sansFont,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
    }
}

@Composable
fun TermsAndServicesSheet(
    termsText: String,
    onDismiss: () -> Unit
) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        // Header Row (Heading + Cancel button)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Terms of Service",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = colorResource(id = R.color.blue)
            )

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onDismiss() }
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = "Cancel",
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content Text
        Text(
            text = termsText,
            fontSize = 14.sp,
            fontFamily = sansFont,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
    }
}


@Composable
fun DailyReminderSheet(onDismiss: () -> Unit) {
    val sansFont = FontFamily(Font(R.font.ptsans_regular, FontWeight.Light))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Set few Reminder",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onDismiss() }
                    .background(
                        color = colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = "Cancel",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "These medication reminders are all better than\nyour average pillbox. From apps to handheld\ntimers",
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp),
            fontFamily = sansFont,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue)
        )

        Column(
            modifier = Modifier.padding(top = 15.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            AddActionRow(
                label = "Midday Rest reminder",
                onAddClick = {
                    // TODO: handle morning add
                }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.blue)
            )

            AddActionRow(
                label = "Bedtime reminder",
                onAddClick = {
                    // TODO: handle evening add
                }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.blue)
            )
        }
    }
}

@Composable
fun ToggleRow(
    label: String,
    toggleState: Boolean,
    onToggleChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = colorResource(id = R.color.blue)
        )

        Box(
            modifier = Modifier
                .width(50.dp)
                .height(26.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (toggleState) colorResource(id = R.color.blue)
                    else Color.LightGray
                )
                .clickable { onToggleChange(!toggleState) },
            contentAlignment = if (toggleState) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .padding(4.dp)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

@Composable
fun AddActionRow(
    label: String,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = colorResource(id = R.color.black)
        )

        Box(
            modifier = Modifier
                .height(50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(colorResource(id = R.color.blue))
                .clickable { onAddClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plus), // 👈 replace with your "add" icon
                    contentDescription = "Add Icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Add New",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

//@Composable
//fun TermsOfServiceSheet() {
//    Column(modifier = Modifier.padding(20.dp)) {
//        Text("Terms of Service", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//        // Content here
//    }

@Composable
fun LabeledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.blue),
                    shape = RoundedCornerShape(8.dp)
                ),
            singleLine = true
        )
    }
}

@Composable
fun ProfileRowItem(
    iconResId: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = colorResource(id = R.color.blue),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = text,
            fontSize = 18.sp,
            color = colorResource(id = R.color.blue),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    val navController = rememberNavController()
    SettingScreen(navController = navController)
}

//@Preview(showBackground = true, name = "Light Mode")
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
//@Composable
//fun SignOutDialogPreview() {
//    CustomSignOutDialog(
//        onDismiss = {},
//        onConfirm = {}
//    )
//}