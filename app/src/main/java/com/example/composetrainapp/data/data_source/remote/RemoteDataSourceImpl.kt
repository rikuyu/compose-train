package com.example.composetrainapp.data.data_source.remote

import com.example.composetrainapp.data.utils.Constants
import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.domain.model.response.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RemoteDataSource {

    override suspend fun getCharacters(): List<Character> {
        if ((1..4).random() == 1) {
            delay(1000L)
            throw Exception()
        }
        return client.get(Constants.URL_CHARACTER).body<Response>().results
    }
}
