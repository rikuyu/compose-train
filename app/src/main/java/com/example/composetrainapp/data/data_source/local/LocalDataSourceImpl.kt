package com.example.composetrainapp.data.data_source.local

import com.example.composetrainapp.data.database.RickAndMortyDao
import com.example.composetrainapp.domain.data_source.local.LocalDataSource
import com.example.composetrainapp.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: RickAndMortyDao,
) : LocalDataSource {

    override suspend fun getFavoriteCharacterList(): Flow<List<Character>> = flow {
        emit(dao.getCharacterList())
    }.flowOn(Dispatchers.IO)

    override suspend fun insertCharacter(character: Character) {
        dao.insert(character)
    }

    override suspend fun deleteCharacter(character: Character) {
        dao.delete(character)
    }
}
