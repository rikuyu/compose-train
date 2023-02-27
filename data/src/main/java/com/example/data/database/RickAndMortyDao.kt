package com.example.data.database

import androidx.room.*
import com.example.model.Character
import kotlinx.coroutines.flow.Flow

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