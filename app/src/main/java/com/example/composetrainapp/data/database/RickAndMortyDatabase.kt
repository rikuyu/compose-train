package com.example.composetrainapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetrainapp.domain.model.Character

@Database(entities = [Character::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun rickAndMortyDao(): RickAndMortyDao
}
