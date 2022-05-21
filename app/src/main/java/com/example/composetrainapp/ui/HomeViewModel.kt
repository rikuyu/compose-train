package com.example.composetrainapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetrainapp.data.utils.Result
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.domain.repository.RickMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RickMortyRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        viewModelScope.launch {
            getCharacters()
        }
    }

    private suspend fun getCharacters() {
        repository.getCharacters().map<List<Character>, Result<List<Character>>> {
            Result.Success(it)
        }.onStart {
            Result.Loading
        }.catch {
            Result.Error(it)
        }.collect { result ->
            _uiState.value = when (result) {
                is Result.Loading -> HomeUiState.Loading
                is Result.Success -> HomeUiState.Success(result.data)
                is Result.Error -> HomeUiState.Error(result.exception)
            }
        }
    }
}

sealed interface HomeUiState {
    data class Success(val data: List<Character>) : HomeUiState
    data class Error(val exception: Throwable? = null) : HomeUiState
    object Loading : HomeUiState
}