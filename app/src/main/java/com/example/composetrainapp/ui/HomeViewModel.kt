package com.example.composetrainapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.domain.repository.RickMortyRepository
import com.example.composetrainapp.ui.utils.UiState
import com.example.composetrainapp.ui.utils.handleData
import com.example.composetrainapp.ui.utils.handleError
import com.example.composetrainapp.ui.utils.startLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RickMortyRepository,
) : ViewModel() {

//    ☆ sealed class 使う例
//    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
//    val uiState: StateFlow<HomeUiState> get() = _uiState

    private val _uiState = MutableStateFlow(UiState<List<Character>>())
    val uiState: StateFlow<UiState<List<Character>>> get() = _uiState

    init {
        viewModelScope.launch {
            getCharacters()
        }
    }

    private suspend fun getCharacters() {
        _uiState.startLoading()
        repository.getCharacters()
            .catch { _uiState.handleError(it) }
            .collect { _uiState.handleData(it) }
    }

//    ☆ sealed class 使う例
//    private suspend fun getCharacters() {
//        repository.getCharacters().map<List<Character>, Result<List<Character>>> {
//            Result.Success(it)
//        }.onStart {
//            Result.Loading
//        }.catch {
//            Result.Error(it)
//        }.collect { result ->
//            _uiState.value = when (result) {
//                is Result.Loading -> HomeUiState.Loading
//                is Result.Success -> HomeUiState.Success(result.data)
//                is Result.Error -> HomeUiState.Error(result.exception)
//            }
//        }
//    }
}

//    ☆ sealed class 使う例
//    sealed interface HomeUiState {
//        data class Success(val data: List<Character>) : HomeUiState
//        data class Error(val exception: Throwable? = null) : HomeUiState
//        object Loading : HomeUiState
//    }