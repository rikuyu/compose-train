package com.example.composetrainapp.data.di

import com.example.composetrainapp.data.data_source.remote.RemoteDataSourceImpl
import com.example.composetrainapp.data.repository.RickMortyRepositoryImpl
import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.repository.RickMortyRepository
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
    abstract fun bindRickMortyRepository(rickMortyRepositoryImpl: RickMortyRepositoryImpl): RickMortyRepository
}
