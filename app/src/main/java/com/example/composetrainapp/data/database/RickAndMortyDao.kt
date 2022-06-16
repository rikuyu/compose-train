package com.example.composetrainapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composetrainapp.domain.model.Character

@Dao
interface RickAndMortyDao {

    @Insert
    fun insert(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("SELECT * FROM characters")
    fun getCharacterList(): List<Character>
}
