package com.example.composetrainapp.data.data_source.remote


import com.example.composetrainapp.data.utils.Constants
import com.example.composetrainapp.domain.data_source.remote.RemoteDataSource
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.domain.model.response.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RemoteDataSource {

    override suspend fun getCharacters(): List<Character> {
     return client.get(Constants.URL_CHARACTER).body<Response>().results
    }
}