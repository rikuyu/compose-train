package com.example.composetrainapp.data.repository

import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.model.Character
import com.example.composetrainapp.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickAndAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : RickAndMortyRepository {

    override suspend fun getCharacters(): Flow<List<Character>> = flow {
        emit(remoteDataSource.getCharacters())
    }.flowOn(Dispatchers.IO)

    override suspend fun getSpecificCharacter(id: Int): Flow<Character> = flow {
        emit(remoteDataSource.getSpecificCharacter(id))
    }.flowOn(Dispatchers.IO)
}
