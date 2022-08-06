package com.example.data.repository

import com.example.domain.data_source.local.LocalDataSource
import com.example.domain.data_source.remote.RemoteDataSource
import com.example.domain.model.Character
import com.example.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : RickAndMortyRepository {

    override suspend fun getCharacterList(): Flow<List<Character>> = flow {
        emit(remoteDataSource.getCharacters())
    }.flowOn(Dispatchers.IO)

    override suspend fun getSpecificCharacter(id: Int): Flow<Character> = flow {
        emit(remoteDataSource.getSpecificCharacter(id))
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