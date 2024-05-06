package com.howest.skyeye

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.howest.skyeye.ui.SkyEyeApp
import com.howest.skyeye.ui.theme.SkyEyeTheme

class MainActivity : ComponentActivity() {
    private val skyEyeApp = SkyEyeApp()
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyEyeTheme(darkTheme = skyEyeApp.isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SkyEyeApp()
                }
            }
        }
    }
}
