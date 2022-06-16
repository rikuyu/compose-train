package com.example.composetrainapp.data.di

import com.example.composetrainapp.data.data_source.local.LocalDataSourceImpl
import com.example.composetrainapp.data.data_source.remote.RemoteDataSourceImpl
import com.example.composetrainapp.data.repository.RickAndAndMortyRepositoryImpl
import com.example.composetrainapp.domain.data_source.local.LocalDataSource
import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRickMortyRepository(rickAndMortyRepositoryImpl: RickAndAndMortyRepositoryImpl): RickAndMortyRepository
}
