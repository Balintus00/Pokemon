package io.github.balintus00.pokemon.data.network

import io.github.balintus00.pokemon.data.network.model.PlainPokemonQueryResponse
import io.github.balintus00.pokemon.data.network.model.PokemonApiModel
import io.github.balintus00.pokemon.data.network.model.PokemonFilterByTypeQueryResponse
import io.github.balintus00.pokemon.di.BaseUrl
import io.github.balintus00.pokemon.repository.data.PokemonType
import io.github.balintus00.pokemon.repository.data.RemotePokemonDataSource
import io.github.balintus00.pokemon.repository.data.RemotePokemonPreview
import io.github.balintus00.pokemon.repository.model.RemotePokemonDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class KtorRemotePokemonDataSource @Inject constructor(
    @BaseUrl private val baseUrl: String,
    private val httpClient: HttpClient,
) : RemotePokemonDataSource {

    override suspend fun getPokemonDetails(pokemonName: String): RemotePokemonDetails {
        val response = httpClient.get(baseUrl) {
            url {
                appendPathSegments(PATH_SEGMENT_POKEMON)
                appendPathSegments(pokemonName)
            }
        }

        return try {
            val apiModel = response.body<PokemonApiModel>()

            apiModel.toRemotePokemonAttributes(pokemonName)
        } catch (e: Exception) {
            // TODO use more concrete Exception type
            throw IllegalArgumentException(
                "No pokemon found with given name: $pokemonName",
                e,
            )
        }
    }

    override suspend fun getPokemonPreviews(
        nameFilter: String,
        typeFilter: String,
    ): List<RemotePokemonPreview> = if (typeFilter.isEmpty()) {
        val pokemonNames = mutableListOf<String>()

        httpClient.get(baseUrl) {
            url {
                appendPathSegments(PATH_SEGMENT_POKEMON)

                parameters.append(QUERY_PARAMETER_LIMIT, AVAILABLE_POKEMON_COUNT.toString())
            }
        }.body<PlainPokemonQueryResponse>()
            .results
            .filter {
                if (nameFilter.isNotEmpty()) {
                    it.name.startsWith(nameFilter)
                } else {
                    true
                }
            }
            .map {
                pokemonNames.add(it.name)

                coroutineScope {
                    async {
                        httpClient.get(it.url).body<PokemonApiModel>()
                    }
                }
            }.awaitAll()
            .mapIndexed { index, nameWithAttributes ->
                RemotePokemonPreview(
                    name = pokemonNames[index],
                    type = nameWithAttributes.types.first().resourceReference.name,
                )
            }
    } else {
        httpClient.get(baseUrl) {
            url {
                appendPathSegments(PATH_SEGMENT_TYPE)
                appendPathSegments(typeFilter)
            }
        }.body<PokemonFilterByTypeQueryResponse>().pokemons.map {
            RemotePokemonPreview(it.pokemon.name, typeFilter)
        }
    }

    private fun PokemonApiModel.toRemotePokemonAttributes(
        pokemonName: String,
        areHiddenAbilitiesRemoved: Boolean = true,
    ): RemotePokemonDetails = RemotePokemonDetails(
        name = pokemonName,
        abilities = abilities
            .filter { ability ->
                if (areHiddenAbilitiesRemoved) !ability.isHidden else true
            }
            .map { ability -> ability.resourceReference.name }.toSet(),
        height = height,
        imageUrl = sprites.defaultUrl,
        type = types.first().resourceReference.name,
        weight = weight,
    )


    override suspend fun getTypes(): List<PokemonType> {
        val response = httpClient.get(baseUrl) {
            url {
                appendPathSegments(PATH_SEGMENT_TYPE)

                parameters.append(QUERY_PARAMETER_LIMIT, AVAILABLE_POKEMON_TYPE_COUNT.toString())
            }
        }

        return response.body<PlainPokemonQueryResponse>().results.map { PokemonType(it.name) }
    }

    companion object {

        private const val AVAILABLE_POKEMON_COUNT = 1302
        private const val AVAILABLE_POKEMON_TYPE_COUNT = 21

        private const val PATH_SEGMENT_POKEMON = "pokemon"
        private const val PATH_SEGMENT_TYPE = "type"

        private const val QUERY_PARAMETER_LIMIT = "limit"
    }
}