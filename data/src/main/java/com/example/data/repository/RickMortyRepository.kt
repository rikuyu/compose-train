package com.example.data.repository

import com.example.model.Character
import kotlinx.coroutines.flow.Flow

interface RickMortyRepository {

    suspend fun getCharacters(): List<Character>

    suspend fun getSpecificCharacter(id: Int): Flow<Character>

    suspend fun getFavoriteCharacters(): List<Character>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)

    suspend fun checkIsExistInFavorite(id: Int): Flow<Boolean>
}