package com.example.ui.home

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RickMortyRepository
import com.example.model.Character
import com.example.model.CharacterDetail
import com.example.ui.utils.getBackgroundColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickMortyViewModel @Inject constructor(
    private val repository: RickMortyRepository,
) : ViewModel() {

    private val _characters = MutableStateFlow(CharactersUiState())
    val characters = _characters.asStateFlow()

    private val _favoriteCharacters = MutableStateFlow(FavoriteCharactersUiState())
    val favoriteCharacters = _favoriteCharacters.asStateFlow()

    private val _backgroundColor: MutableStateFlow<Color> = MutableStateFlow(getBackgroundColor(null))
    private val _character: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState())
    private val _isFavorite: MutableStateFlow<FavoriteCharacterUiState> = MutableStateFlow(FavoriteCharacterUiState())

    val characterDetail: StateFlow<CharacterDetailUiState> = combine(
        _character,
        _isFavorite,
        _backgroundColor,
    ) { c, f, b ->
        val isLoading = c.isLoading || f.isLoading
        val error = c.error ?: f.error
        return@combine if (isLoading) {
            CharacterDetailUiState(isLoading = isLoading)
        } else if (error != null) {
            CharacterDetailUiState(isLoading = false, error = error)
        } else {
            CharacterDetailUiState(
                isLoading = false,
                detail = CharacterDetail.convertToDetail(
                    character = c.character,
                    isFavorite = f.isFavorite,
                    backgroundColor = b,
                ),
                error = null,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CharacterDetailUiState(),
    )

    fun getCharacters(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _characters.update {
                if (isRefreshing) {
                    it.copy(isRefreshing = true, error = null)
                } else {
                    it.copy(isLoading = true, error = null)
                }
            }
            runCatching { repository.getCharacters() }
                .onSuccess { c ->
                    _characters.update {
                        if (isRefreshing) {
                            it.copy(isRefreshing = false, characters = c)
                        } else {
                            it.copy(isLoading = false, characters = c)
                        }
                    }
                }
                .onFailure { t ->
                    _characters.update {
                        if (isRefreshing) {
                            it.copy(isRefreshing = false, error = t)
                        } else {
                            it.copy(isLoading = false, error = t)
                        }
                    }
                }
        }
    }

    fun refreshCharacters() {
        getCharacters(isRefreshing = true)
    }

    private fun getCharacter(id: Int) {
        viewModelScope.launch {
            _character.update { it.copy(isLoading = true) }
            runCatching { repository.getCharacter(id) }
                .onSuccess { c ->
                    _character.update {
                        it.copy(isLoading = false, character = c)
                    }
                    _backgroundColor.update { getBackgroundColor(c.gender) }
                }
                .onFailure { t -> _character.update { it.copy(isLoading = false, error = t) } }
        }
    }

    private fun getIsFavorite(id: Int) {
        viewModelScope.launch {
            _isFavorite.update { it.copy(isLoading = true) }
            runCatching { repository.getIsFavorite(id) }
                .onSuccess { b ->
                    _isFavorite.update { it.copy(isLoading = false, isFavorite = b) }
                }
                .onFailure { t ->
                    _isFavorite.update { it.copy(isLoading = false, error = t) }
                }
        }
    }

    fun getDetail(id: Int) {
        getCharacter(id)
        getIsFavorite(id)
    }

    fun onClickFavorite(
        isClicked: Boolean,
        character: Character,
        isFavoriteScreen: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isClicked) {
                addFavorite(character)
            } else {
                deleteFavorite(character)
                if (isFavoriteScreen) getFavoriteCharacters()
            }
        }
    }

    private suspend fun addFavorite(character: Character) {
        runCatching { repository.insertCharacter(character) }
            .onSuccess { getIsFavorite(character.id) }
    }

    private suspend fun deleteFavorite(character: Character) {
        runCatching { repository.deleteCharacter(character) }
            .onSuccess { getIsFavorite(character.id) }
    }

    fun getFavoriteCharacters() {
        viewModelScope.launch {
            _favoriteCharacters.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.getFavoriteCharacters() }
                .onSuccess { c ->
                    _favoriteCharacters.update {
                        it.copy(isLoading = false, characters = c)
                    }
                }
                .onFailure { t ->
                    _favoriteCharacters.update {
                        it.copy(isLoading = false, error = t)
                    }
                }
        }
    }
}

@Stable
data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val isRefreshing: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
) : UiState

@Stable
data class CharacterUiState(
    val character: Character? = null,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
) : UiState

@Stable
data class FavoriteCharactersUiState(
    val characters: List<Character> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
) : UiState

@Stable
data class FavoriteCharacterUiState(
    val isFavorite: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
) : UiState

@Stable
data class CharacterDetailUiState(
    val detail: CharacterDetail? = null,
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
) : UiState

@Stable
interface UiState {
    val isLoading: Boolean
    val error: Throwable?
}