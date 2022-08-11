package com.example.data.di

import com.example.data.data_source.local.LocalDataSourceImpl
import com.example.data.data_source.remote.RickMortyDataSourceImpl
import com.example.data.repository.FirebaseRepositoryImpl
import com.example.data.repository.RickMortyRepositoryImpl
import com.example.domain.data_source.local.LocalDataSource
import com.example.domain.data_source.remote.RickMortyDataSource
import com.example.domain.repository.FirebaseRepository
import com.example.domain.repository.RickMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteDataSource(rickMortyDataSourceImpl: RickMortyDataSourceImpl): RickMortyDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRickMortyRepository(rickAndMortyRepositoryImpl: RickMortyRepositoryImpl): RickMortyRepository

    @Binds
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository
}