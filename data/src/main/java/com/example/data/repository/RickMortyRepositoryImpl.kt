package com.example.data.repository

import com.example.data.data_source.local.LocalDataSource
import com.example.data.data_source.remote.RickMortyDataSource
import com.example.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickMortyRepositoryImpl @Inject constructor(
    private val rickMortyDataSource: RickMortyDataSource,
    private val localDataSource: LocalDataSource,
) : RickMortyRepository {

    override suspend fun getCharacterList(): Flow<List<Character>> = flow {
        emit(rickMortyDataSource.getCharacters())
    }.flowOn(Dispatchers.IO)

    override suspend fun getSpecificCharacter(id: Int): Flow<Character> = flow {
        emit(rickMortyDataSource.getSpecificCharacter(id))
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavoriteCharacterList(): Flow<List<Character>> =
        localDataSource.getFavoriteCharacterList()

    override suspend fun insertCharacter(character: Character) {
        localDataSource.insertCharacter(character)
    }

    override suspend fun deleteCharacter(character: Character) {
        localDataSource.deleteCharacter(character)
    }

    override suspend fun checkIsExistInFavorite(id: Int): Flow<Boolean> = localDataSource.checkIsExistInFavorite(id)
}