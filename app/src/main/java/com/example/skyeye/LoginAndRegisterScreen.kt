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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skyeye.ui.theme.SkyEyeTheme


@Composable
fun LoginAndRegisterScreen(navController: NavController, isRegister: Boolean) {
    var greeting = "Log in to your SkyEye account"
    var actionWord = "Log in"
    if (isRegister) {
        greeting = "Create one SkyEye account for all your devices"
        actionWord = "Sign up"
    }

    SkyEyeTheme {
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
                val logo = if (isSystemInDarkTheme()) R.drawable.logo_skyeye else R.drawable.logo_skyeye_light
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(text = greeting, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(0.75f))
                Column {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(0.5.dp, Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google__g__logo_1_),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(70.dp))
                            Text(
                                text = "$actionWord with Google",
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4285F4),
                            contentColor = Color.White
                        ),
                        border = BorderStroke(0.5.dp, Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconmonstr_facebook_1),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(60.dp))
                            Text(
                                text = "$actionWord with Facebook",
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(0.5.dp, Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.apple_logo_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(75.dp))
                            Text(
                                text = "$actionWord  with Apple",
                                textAlign = TextAlign.End
                            )
                        }
                    }
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
                            TextButton(onClick = { /*TODO*/ }) {
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
}


















