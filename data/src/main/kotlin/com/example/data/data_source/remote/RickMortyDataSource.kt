package com.example.data.data_source.remote

import com.example.model.Character

interface RickMortyDataSource {

    suspend fun getCharacters(): List<Character>

    suspend fun getCharacter(id: Int): Character
}
