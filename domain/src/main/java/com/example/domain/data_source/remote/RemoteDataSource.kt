package com.example.domain.data_source.remote

import com.example.domain.model.Character

interface RemoteDataSource {

    suspend fun getCharacters(): List<Character>

    suspend fun getSpecificCharacter(id: Int): Character
}