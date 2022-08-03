package com.example.composetrainapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetrainapp.domain.model.Character
import com.example.composetrainapp.domain.model.DetailCharacter
import com.example.composetrainapp.domain.repository.RickAndMortyRepository
import com.example.composetrainapp.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickMortyViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
) : ViewModel() {

    private val _characterListState = MutableStateFlow(UiState<List<Character>>())
    val characterListState: StateFlow<UiState<List<Character>>> get() = _characterListState

    private val _backgroundColor: MutableState<Color> = mutableStateOf(getBackgroundColor(null))
    val backgroundColor: State<Color> get() = _backgroundColor

    private val _favoriteCharacterState = MutableStateFlow(UiState<List<Character>>())
    val favoriteCharacterState: StateFlow<UiState<List<Character>>> get() = _favoriteCharacterState

    private val _characterState = MutableStateFlow(UiState<Character>())
    private val _isExistInFavorite = MutableStateFlow(false)

    val characterDetailState: StateFlow<DetailUiState<DetailCharacter>> = combine(
        _characterState,
        _isExistInFavorite
    ) { remoteCharacterData, isExistInFavorite ->
        return@combine when (val state = remoteCharacterData.convertState()) {
            is DetailUiState.Loading -> DetailUiState.Loading()
            is DetailUiState.Error -> DetailUiState.Error(state.exception)
            is DetailUiState.Success -> {
                DetailUiState.Success(
                    DetailCharacter.convertToDetail(state.data, isExistInFavorite)
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailUiState.Loading()
    )

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

    private fun getSpecificCharacter(id: Int) {
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

    private fun checkIsExistInFavorite(id: Int) {
        viewModelScope.launch {
            repository.checkIsExistInFavorite(id)
                .catch { _isExistInFavorite.value = false }
                .collect { _isExistInFavorite.value = it }
        }
    }

    fun getDetail(id: Int) {
        getSpecificCharacter(id)
        checkIsExistInFavorite(id)
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

    fun getFavoriteCharacterList() {
        _favoriteCharacterState.startLoading(LoadingState.LOADING)
        viewModelScope.launch {
            repository.getFavoriteCharacterList()
                .catch { _favoriteCharacterState.handleError(it) }
                .collect { _favoriteCharacterState.handleData(it) }
        }
    }
}

sealed class DetailUiState<T> {
    data class Success<T>(val data: T) : DetailUiState<T>()
    data class Error<T>(val exception: Throwable) : DetailUiState<T>()
    class Loading<T> : DetailUiState<T>()
}