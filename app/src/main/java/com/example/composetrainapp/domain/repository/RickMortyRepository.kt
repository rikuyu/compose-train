package com.example.composetrainapp.domain.repository

import com.example.composetrainapp.domain.model.response.Character
import kotlinx.coroutines.flow.Flow

interface RickMortyRepository {

    suspend fun getCharacters(): Flow<List<Character>>
}