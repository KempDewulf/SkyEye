package com.howest.skyeye

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.SkyEyeApp
import com.howest.skyeye.ui.core.MainViewModel
import com.howest.skyeye.ui.theme.SkyEyeTheme
import com.howest.skyeye.workers.ReminderWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(2, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "skyEyeReminder",
            ExistingWorkPolicy.REPLACE,
            reminderWorkRequest
        )

        setContent {
            val mainViewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val mainUiState by mainViewModel.mainUiState.collectAsState()

            SkyEyeTheme(darkTheme = mainUiState.isDarkMode) {
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
