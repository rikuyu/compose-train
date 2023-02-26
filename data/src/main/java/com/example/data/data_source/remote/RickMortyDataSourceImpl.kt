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

    private enum class Status { SUCCESS, ERROR }

    private var status = Status.ERROR

    override suspend fun getCharacters(): List<Character> {
        if (status == Status.ERROR) {
            status = Status.SUCCESS
            delay(1000L)
            throw Exception()
        }
        return client.get(Constants.URL_CHARACTER).body<Response>().results.shuffled()
    }

    override suspend fun getSpecificCharacter(id: Int): Character =
        client.get("${Constants.URL_CHARACTER}/$id").body()
}