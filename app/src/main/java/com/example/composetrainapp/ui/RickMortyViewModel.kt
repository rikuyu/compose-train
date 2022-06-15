package com.example.composetrainapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetrainapp.domain.model.Character
import com.example.composetrainapp.domain.repository.RickAndMortyRepository
import com.example.composetrainapp.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickMortyViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
) : ViewModel() {

//    ☆ sealed class 使う例
//    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
//    val uiState: StateFlow<HomeUiState> get() = _uiState

    private val _characterListState = MutableStateFlow(UiState<List<Character>>())
    val characterListState: StateFlow<UiState<List<Character>>> get() = _characterListState

    private val _characterState = MutableStateFlow(UiState<Character>())
    val characterState: StateFlow<UiState<Character>> get() = _characterState

    init {
        getCharacters()
    }

    fun getCharacters(loadingState: LoadingState = LoadingState.LOADING) {
        _characterListState.startLoading(loadingState)
        viewModelScope.launch {
            repository.getCharacters()
                .catch { _characterListState.handleError(it) }
                .collect { _characterListState.handleData(it) }
        }
    }

    fun refreshCharacters() {
        getCharacters(LoadingState.REFRESHING)
    }

    fun getSpecificCharacter(id: Int) {
        _characterState.startLoading(LoadingState.LOADING)
        viewModelScope.launch {
            repository.getSpecificCharacter(id)
                .catch { _characterState.handleError(it) }
                .collect { _characterState.handleData(it) }
        }
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
