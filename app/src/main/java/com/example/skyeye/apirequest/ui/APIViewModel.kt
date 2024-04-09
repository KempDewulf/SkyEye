package com.example.skyeye.apirequest.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyeye.apirequest.network.skyEyeApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface APIUiState {
    data class Success(val data: String) : APIUiState
    object Error : APIUiState
    object Loading : APIUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class APIViewModel : ViewModel() {
    var apiUiState: APIUiState by mutableStateOf(APIUiState.Loading)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getAirportData(icao: String) {
        viewModelScope.launch {
            apiUiState = APIUiState.Loading
            apiUiState = try {
                val result = skyEyeApi.retrofitService.getAirportData(icao)
                APIUiState.Success(
                    "${result.toString()}"
                )
            } catch (e: IOException) {
                Log.e("ERROR", "${e.message}");
                APIUiState.Error
            } catch (e: HttpException) {
                Log.e("HTTP ERROR", "${e.message}");
                APIUiState.Error
            }
        }
    }
}
