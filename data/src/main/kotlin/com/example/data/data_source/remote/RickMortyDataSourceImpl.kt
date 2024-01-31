package com.example.data.data_source.remote

import com.example.data.utils.Constants
import com.example.model.Character
import com.example.model.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class RickMortyDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RickMortyDataSource {

    override suspend fun getCharacters(): List<Character> =
        client.get(Constants.URL_CHARACTER).body<Response>().results.shuffled()

    override suspend fun getCharacter(id: Int): Character =
        client.get("${Constants.URL_CHARACTER}/$id").body()
}
