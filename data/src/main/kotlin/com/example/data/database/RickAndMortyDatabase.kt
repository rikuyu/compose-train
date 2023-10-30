package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.model.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun rickAndMortyDao(): RickAndMortyDao
}
