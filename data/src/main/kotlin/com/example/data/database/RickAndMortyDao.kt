package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.Character

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("SELECT * FROM characters")
    fun getFavoriteCharacters(): List<Character>

    @Query("SELECT EXISTS(SELECT * FROM characters WHERE id = :id)")
    fun getIsFavorite(id: Int): Boolean
}
