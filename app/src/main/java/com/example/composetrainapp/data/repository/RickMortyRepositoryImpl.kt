package com.example.composetrainapp.data.repository

import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.domain.repository.RickMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : RickMortyRepository {

    override suspend fun getCharacters(): Flow<List<Character>> = flow {
        emit(remoteDataSource.getCharacters())
    }.flowOn(Dispatchers.IO)
}
