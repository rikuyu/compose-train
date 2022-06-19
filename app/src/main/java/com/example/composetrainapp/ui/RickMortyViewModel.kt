package com.example.composetrainapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
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

    private val _backgroundColor: MutableState<Color> = mutableStateOf(getBackgroundColor(null))
    val backgroundColor: State<Color> get() = _backgroundColor

    private val _favoriteCharacterState = MutableStateFlow(UiState<List<Character>>())
    val favoriteCharacterState: StateFlow<UiState<List<Character>>> get() = _favoriteCharacterState

//    val characterDetailState: StateFlow<UiState<DetailCharacter>> = combine(
//        repository.getSpecificCharacter(id),
//        repository.checkIsExistInFavorite(id)
//    ){
//
//    }

    fun getCharacters(loadingState: LoadingState = LoadingState.LOADING) {
        _characterListState.startLoading(loadingState)
        viewModelScope.launch {
            repository.getCharacterList()
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
                .collect {
                    _characterState.handleData(it)
                    _backgroundColor.value = getBackgroundColor(it.gender)
                }
        }
    }

    fun onClickEvent(isClicked: Boolean, character: Character) {
        viewModelScope.launch {
            if (isClicked) {
                addFavoriteCharacter(character)
            } else {
                deleteFavoriteCharacter(character)
            }
        }
    }

    private suspend fun addFavoriteCharacter(character: Character) {
        repository.insertCharacter(character)
    }

    private suspend fun deleteFavoriteCharacter(character: Character) {
        repository.deleteCharacter(character)
    }

    suspend fun getFavoriteCharacters() {
        _favoriteCharacterState.startLoading(LoadingState.LOADING)
        viewModelScope.launch {
            repository.getFavoriteCharacterList()
                .catch { _favoriteCharacterState.handleError(it) }
                .collect { _favoriteCharacterState.handleData(it) }
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