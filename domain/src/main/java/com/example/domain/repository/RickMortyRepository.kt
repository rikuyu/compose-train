package com.example.domain.repository

import com.example.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface RickMortyRepository {

    suspend fun getCharacterList(): Flow<List<Character>>

    suspend fun getSpecificCharacter(id: Int): Flow<Character>

    suspend fun getFavoriteCharacterList(): Flow<List<Character>>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)

    suspend fun checkIsExistInFavorite(id: Int): Flow<Boolean>
}