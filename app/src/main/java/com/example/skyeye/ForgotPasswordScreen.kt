package com.example.skyeye


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skyeye.ui.theme.SkyEyeTheme


@Composable
fun ForgotPasswordScreen(navController: NavController, isDarkMode: Boolean) {
    var greeting = "Forgot your password?"
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)) {
                IconButton(onClick = { navController.navigate("home")}) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            val logo = if (isDarkMode) R.drawable.logo_skyeye else R.drawable.logo_skyeye_light
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.64f)
                    .padding(0.dp, 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = greeting,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                )
                Text(
                    text = "Enter your email to request a new password",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                var email by remember { mutableStateOf("") }

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                )

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        Text(text = "Request new password")
                    }
                }
            }
        }
    }
}
