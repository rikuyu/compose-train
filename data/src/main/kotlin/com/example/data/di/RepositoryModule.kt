package com.example.data.di

import com.example.data.repository.FirebaseRepository
import com.example.data.repository.FirebaseRepositoryImpl
import com.example.data.repository.RickMortyRepository
import com.example.data.repository.RickMortyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRickMortyRepository(rickAndMortyRepositoryImpl: RickMortyRepositoryImpl): RickMortyRepository

    @Binds
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository
}
