package com.example.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Character
import com.example.domain.model.DetailCharacter
import com.example.domain.repository.RickAndMortyRepository
import com.example.ui.utils.*
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

    private val _characterState: MutableStateFlow<DetailState<Character>> = MutableStateFlow(DetailState.Loading())
    private val _isExistInFavorite: MutableStateFlow<DetailState<Boolean>> = MutableStateFlow(DetailState.Loading())

    val characterDetailState: StateFlow<DetailState<DetailCharacter>> = combine(
        _characterState,
        _isExistInFavorite
    ) { remoteCharacterData, isExistInFavorite ->
        val data = if (remoteCharacterData is DetailState.Loading || isExistInFavorite is DetailState.Loading) {
            DetailState.Loading()
        } else if (remoteCharacterData is DetailState.Error) {
            DetailState.Error(remoteCharacterData.exception)
        } else if (isExistInFavorite is DetailState.Error) {
            DetailState.Error(isExistInFavorite.exception)
        } else if (remoteCharacterData is DetailState.Success && isExistInFavorite is DetailState.Success) {
            DetailState.Success(DetailCharacter.convertToDetail(remoteCharacterData.data, isExistInFavorite.data))
        } else {
            DetailState.Error(Exception("unknown error"))
        }
        return@combine data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailState.Loading()
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
        viewModelScope.launch {
            repository.getSpecificCharacter(id)
                .catch { _characterState.value = DetailState.Error(it) }
                .collect {
                    _characterState.value = DetailState.Success(it)
                    _backgroundColor.value = getBackgroundColor(it.gender)
                }
        }
    }

    private fun checkIsExistInFavorite(id: Int) {
        viewModelScope.launch {
            repository.checkIsExistInFavorite(id)
                .catch { _isExistInFavorite.value = DetailState.Error(it) }
                .collect { _isExistInFavorite.value = DetailState.Success(it) }
        }
    }

    fun getDetail(id: Int) {
        getSpecificCharacter(id)
        checkIsExistInFavorite(id)
    }

    fun onClickHeartIconEvent(isClicked: Boolean, character: Character, isFavoriteScreen: Boolean = false) {
        viewModelScope.launch {
            if (isClicked) {
                addFavoriteCharacter(character)
            } else {
                deleteFavoriteCharacter(character)
                if (isFavoriteScreen) getFavoriteCharacterList()
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

sealed class DetailState<T> {
    data class Success<T>(val data: T) : DetailState<T>()
    data class Error<T>(val exception: Throwable) : DetailState<T>()
    class Loading<T> : DetailState<T>()
}