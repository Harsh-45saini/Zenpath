package com.example.zenpath.ui.auth

// AuthComponents.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenpath.R

@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Enter your email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E6FF), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.email),
                contentDescription = "Email",
                tint = colorResource(id = R.color.blue)
            )
        },
        textStyle = TextStyle(
            color = colorResource(id = R.color.blue),
            fontSize = 14.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.blue),
            unfocusedLabelColor = colorResource(id = R.color.blue),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
fun NameTextField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Full Name") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E6FF), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                tint = colorResource(id = R.color.blue)
            )
        },
        textStyle = TextStyle(
            color = colorResource(id = R.color.blue),
            fontSize = 14.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.blue),
            unfocusedLabelColor = colorResource(id = R.color.blue),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(password: String, onPasswordChanged: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) } // state for visibility toggle

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color(0xFFE0E6FF), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = "Password",
                tint = colorResource(id = R.color.blue)
            )
        },
        trailingIcon = {
            val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility)
            else painterResource(id = R.drawable.ic_visibility_off)
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, contentDescription = description, tint = colorResource(id = R.color.blue))
            }
        },
        textStyle = TextStyle(
            color = colorResource(id = R.color.blue),
            fontSize = 14.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.blue),
            unfocusedLabelColor = colorResource(id = R.color.blue),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
fun RoundedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    cornerRadius: Dp = 6.dp,
    borderColor: Color = colorResource(id = R.color.blue),
    checkedColor: Color = Color.Blue
) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                width = 2.dp,
                color = if (checked) checkedColor else borderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(
                color = if (checked) checkedColor else Color.Transparent,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}