package com.example.data.data_source.local

import com.example.data.database.RickAndMortyDao
import com.example.model.Character
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: RickAndMortyDao,
) : LocalDataSource {

    override suspend fun getFavoriteCharacters(): List<Character> = dao.getFavoriteCharacters()

    override suspend fun insertCharacter(character: Character) {
        dao.insert(character)
    }

    override suspend fun deleteCharacter(character: Character) {
        dao.delete(character)
    }

    override suspend fun getIsFavorite(id: Int): Boolean = dao.getIsFavorite(id)
}
