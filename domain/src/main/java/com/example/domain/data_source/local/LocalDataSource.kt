package com.example.domain.data_source.local

import com.example.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getFavoriteCharacterList(): Flow<List<Character>>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)

    suspend fun checkIsExistInFavorite(id: Int): Flow<Boolean>
}