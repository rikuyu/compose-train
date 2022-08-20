package com.example.data.di

import com.example.data.data_source.local.LocalDataSource
import com.example.data.data_source.local.LocalDataSourceImpl
import com.example.data.data_source.remote.FirebaseDataSource
import com.example.data.data_source.remote.FirebaseDataSourceImpl
import com.example.data.data_source.remote.RickMortyDataSource
import com.example.data.data_source.remote.RickMortyDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRickMortyDataSource(rickMortyDataSourceImpl: RickMortyDataSourceImpl): RickMortyDataSource

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}