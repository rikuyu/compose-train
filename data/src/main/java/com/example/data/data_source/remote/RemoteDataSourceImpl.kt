package com.example.data.data_source.remote

import com.example.data.utils.Constants
import com.example.domain.data_source.remote.RemoteDataSource
import com.example.domain.model.Character
import com.example.domain.model.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RemoteDataSource {

    override suspend fun getCharacters(): List<Character> {
        if ((1..8).random() == 1) {
            delay(1000L)
            throw Exception()
        }
        return client.get(Constants.URL_CHARACTER).body<Response>().results
    }

    override suspend fun getSpecificCharacter(id: Int): Character {
        if ((1..8).random() == 1) {
            delay(1000L)
            throw Exception()
        }
        return client.get("${Constants.URL_CHARACTER}/$id").body()
    }
}