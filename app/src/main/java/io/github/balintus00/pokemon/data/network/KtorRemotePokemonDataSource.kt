package io.github.balintus00.pokemon.data.network

import io.github.balintus00.pokemon.di.BaseUrl
import io.github.balintus00.pokemon.repository.data.RemotePokemonDataSource
import io.ktor.client.HttpClient
import javax.inject.Inject

class KtorRemotePokemonDataSource @Inject constructor(
    @BaseUrl private val baseUrl: String,
    private val httpClient: HttpClient,
) : RemotePokemonDataSource