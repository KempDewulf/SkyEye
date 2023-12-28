package com.example.skyeye

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Icon(Icons.Rounded.Face, contentDescription = null)
        Text(text = "Log in to your SkyEye account")
        Column {
            Button(onClick = { /*TODO*/ }) {
                Row {
                    // Google Icon
                    Text(text = "Log in with Google")
                }
            }
            Button(onClick = { /*TODO*/ }) {
                Row {
                    // Google Icon
                    Text(text = "Log in with Facebook")
                }
            }
            Button(onClick = { /*TODO*/ }) {
                Row {
                    // Google Icon
                    Text(text = "Log in with Apple")
                }
            }
        }
        Text(text = "or with email")
        Column {
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Email") }
            )
        }
        Row {
            Text(text = "Don't have an account?")
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Sign up")
            }
        }
    }
}