// AuthScreen.kt
package com.example.zenpath

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenpath.data.repository.AuthRepository
import com.example.zenpath.ui.auth.EmailTextField
import com.example.zenpath.ui.viewmodel.LoginViewModel
import com.example.zenpath.ui.viewmodel.LoginViewModelFactory
import com.example.zenpath.ui.auth.NameTextField
import com.example.zenpath.ui.auth.PasswordField
import com.example.zenpath.ui.viewmodel.RegisterViewModel
import com.example.zenpath.ui.viewmodel.RegisterViewModelFactory
import com.example.zenpath.ui.auth.RoundedCheckbox

@Composable
fun AuthScreen(navController: NavHostController) {
    val ptSerif = FontFamily(Font(R.font.ptserif_regular, FontWeight.Normal))
    val ptSans = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Login", "Register")

    // ViewModels
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(AuthRepository())
    )
    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(AuthRepository())
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 45.dp,
                    bottom = 30.dp,
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = if (selectedTabIndex == 0) "Sign In" else "Register",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ptSerif,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
            )

            // Subtitle (you can update this dynamically too if needed)
            Text(
                text = "Sign in now to access your \nexercises and saved music.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ptSans,
                fontSize = 20.sp,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = colorResource(id = R.color.light_blue),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                val tabCornerShape = RoundedCornerShape(12.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(id = R.color.blue))
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        indicator = {},
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                modifier = Modifier.padding(6.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(tabCornerShape)
                                        .background(
                                            if (selectedTabIndex == index) Color.White else Color.Transparent
                                        )
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = title,
                                        fontSize = 14.sp,
                                        fontFamily = ptSans,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedTabIndex == index) colorResource(id = R.color.blue) else Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Switching between login and register forms
                when (selectedTabIndex) {
                    0 -> LoginForm(viewModel = loginViewModel, navController = navController)
                    1 -> RegisterForm(viewModel = registerViewModel, navController = navController)
                }
            }
        }

        // Bottom Image
        Image(
            painter = painterResource(id = R.drawable.group2),
            contentDescription = "Side Illustration",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 30.dp)
                .size(45.dp)
        )
    }
}

@Composable
fun LoginForm(viewModel: LoginViewModel, navController: NavHostController) {
    val ptSans = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))
    val context = LocalContext.current

    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginSuccess by viewModel.isLoginSuccessful.collectAsState()

    Column {
        EmailTextField(value = username, onValueChange = viewModel::onUsernameChanged)
        PasswordField(password = password, onPasswordChanged = viewModel::onPasswordChanged)

        Text(
            text = "Forgot Password?",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = ptSans,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                if (viewModel.validateCredentials()) {
                    Toast.makeText(context, "Logging In...", Toast.LENGTH_SHORT).show()
                    viewModel.loginUser(context)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text("Sign In", color = colorResource(id = R.color.blue), fontFamily = ptSans, fontWeight = FontWeight.Bold)
        }

        LaunchedEffect(loginSuccess) {
            if (loginSuccess) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun RegisterForm(viewModel: RegisterViewModel, navController: NavHostController) {
    val ptSans = FontFamily(Font(R.font.ptsans_regular, FontWeight.Normal))
    val context = LocalContext.current

    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val registerSuccess by viewModel.isRegisterSuccessful.collectAsState()

    var checked by remember { mutableStateOf(false) }

    Column {
        NameTextField(value = fullName, onValueChange = viewModel::onFullNameChanged)
        Spacer(modifier = Modifier.height(12.dp))
        EmailTextField(value = email, onValueChange = viewModel::onEmailChanged)
        PasswordField(password = password, onPasswordChanged = viewModel::onPasswordChanged)

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedCheckbox(
                checked = checked,
                onCheckedChange = { checked = it },
                cornerRadius = 8.dp,
                borderColor = colorResource(id = R.color.blue),
                checkedColor = colorResource(id = R.color.blue),
            )
            Text("I accepted ", modifier = Modifier.padding(start = 5.dp),fontSize = 14.sp, color = colorResource(id = R.color.blue), fontFamily = ptSans)
            Text("Terms & Privacy Policy", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.blue), fontFamily = ptSans)
        }

        Button(
            onClick = {
                if (viewModel.validateRegisterFields()) {
                    Toast.makeText(context, "Registering...", Toast.LENGTH_SHORT).show()
                    viewModel.registerUser(context)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text("Continue", color = colorResource(id = R.color.blue), fontFamily = ptSans, fontWeight = FontWeight.Bold)
        }

        LaunchedEffect(registerSuccess) {
            if (registerSuccess) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Auth Screen - Login")
@Composable
fun AuthScreenLoginPreview() {
    AuthScreen(navController = rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true, name = "Auth Screen - Register")
@Composable
fun AuthScreenRegisterPreview() {
    AuthScreen(navController = rememberNavController())
}
