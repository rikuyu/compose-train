package com.example.composetrainapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.composetrainapp.data.database.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(@ApplicationContext appContext: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            appContext,
            RickAndMortyDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDao(db: RickAndMortyDatabase) = db.rickAndMortyDao()

    private const val DATABASE_NAME = "characters_database"
}
