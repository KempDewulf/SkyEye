package com.howest.skyeye.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howest.skyeye.data.userpreferences.UserPreferences
import com.howest.skyeye.data.userpreferences.UserPreferencesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface) : ViewModel() {
    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepositoryInterface.userPreferences.collect { userPreferences ->
                _mainUiState.value = MainUiState(isDarkMode = userPreferences?.is_dark_mode ?: false)
            }
        }
    }

    suspend fun toggleDarkMode() {
        val current = _mainUiState.value
        _mainUiState.value = current.copy(isDarkMode = !current.isDarkMode)

        val id = userPreferencesRepositoryInterface.getLastInsertedId() ?: 0
        val newUserPreferences = UserPreferences(id = id, is_dark_mode = _mainUiState.value.isDarkMode)

        if (id == 0) {
            userPreferencesRepositoryInterface.insertUserPreferences(newUserPreferences)
        } else {
            userPreferencesRepositoryInterface.updateUserPreferences(newUserPreferences)
        }
    }
}