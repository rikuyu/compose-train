package com.example.composetrainapp.domain.data_source.local

import com.example.composetrainapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getFavoriteCharacterList(): Flow<List<Character>>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)
}