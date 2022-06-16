package com.example.composetrainapp.data.database

import androidx.room.TypeConverter

class RoomTypeConverters {

    companion object {

        @JvmStatic
        @TypeConverter
        fun charactersStringToList(characters: String): List<String> =
            characters.split(",").map { it.trim() }

        @JvmStatic
        @TypeConverter
        fun listToCharacters(list: List<String>) = list.toTypedArray().joinToString(",")
    }
}
