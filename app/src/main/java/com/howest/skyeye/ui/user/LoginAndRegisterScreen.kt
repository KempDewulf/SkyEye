package com.howest.skyeye.ui.user


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.core.MainViewModel
import howest.nma.skyeye.R


@Composable
fun LoginAndRegisterScreen(navController: NavController, isRegister: Boolean, viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    var greeting = "Log in to your SkyEye account"
    var actionWord = "Log in"
    if (isRegister) {
        greeting = "Create one SkyEye account for all your devices"
        actionWord = "Sign up"
    }
    val mainUiState by viewModel.mainUiState.collectAsState()
    val isDarkMode = mainUiState.isDarkMode

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
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
                modifier = Modifier.size(80.dp)
            )
            Text(text = greeting, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(0.75f))
            Column {
                SocialMediaButton(
                    actionWord = actionWord,
                    platformName = "Google",
                    platformLogo = R.drawable.google__g__logo_1_,
                    isDarkMode = isDarkMode
                )
                SocialMediaButton(
                    actionWord = actionWord,
                    platformName = "Facebook",
                    platformLogo = R.drawable.iconmonstr_facebook_1,
                    containerColor = Color(0xFF4285F4),
                    isDarkMode = isDarkMode
                )
                SocialMediaButton(
                    actionWord = actionWord,
                    platformName = "Apple",
                    platformLogo = R.drawable.apple_logo_svgrepo_com,
                    isDarkMode = isDarkMode
                )
            }
            Text(text = "or with email", fontSize = 18.sp)
            Column(
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisibility by remember { mutableStateOf(false) }

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text("Password") },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painterResource(
                                    id = if (passwordVisibility) {
                                        R.drawable.eye
                                    } else {
                                        R.drawable.eye
                                    }
                                ),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (!isRegister) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { navController.navigate("forgotPassword")}) {
                            Text(text = "Forgot password?")
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        Text(text = actionWord)
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = if (isRegister) "Already have an account?" else "Don't have an account?",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(bottom = 1.dp)
                )
                TextButton(onClick = { navController.navigate(if (isRegister) "login" else "register") }) {
                    Text(text = if (isRegister) "Sign in" else "Sign up", fontSize = 18.sp, textDecoration = TextDecoration.Underline)
                }
            }
        }
    }
}

@Composable
fun SocialMediaButton(
    actionWord: String,
    platformName: String,
    platformLogo: Int,
    containerColor: Color = MaterialTheme.colorScheme.inverseSurface,
    isDarkMode: Boolean
) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        border = if (isDarkMode) BorderStroke(0.5.dp, Color.DarkGray) else null,
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = platformLogo),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                colorFilter = if ((platformName == "Apple" || platformName == "Facebook") && !isDarkMode) ColorFilter.tint(Color.White) else null
            )
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                text = "$actionWord with $platformName",
                textAlign = TextAlign.End
            )
        }
    }
}