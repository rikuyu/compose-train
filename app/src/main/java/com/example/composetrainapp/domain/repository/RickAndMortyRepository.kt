package com.example.composetrainapp.domain.repository

import com.example.composetrainapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharacters(): Flow<List<Character>>

    suspend fun getSpecificCharacter(id: Int): Flow<Character>
}
