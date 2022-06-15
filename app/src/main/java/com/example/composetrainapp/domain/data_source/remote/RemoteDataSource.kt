package com.example.composetrainapp.domain.data_source.remote

import com.example.composetrainapp.domain.model.response.Character

interface RemoteDataSource {

    suspend fun getCharacters(): List<Character>

    suspend fun getSpecificCharacter(id: Int): Character
}
