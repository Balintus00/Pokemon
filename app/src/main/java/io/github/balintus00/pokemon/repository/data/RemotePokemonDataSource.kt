package io.github.balintus00.pokemon.repository.data

import io.github.balintus00.pokemon.repository.model.RemotePokemonDetails

interface RemotePokemonDataSource {

    suspend fun getPokemonDetails(pokemonName: String): RemotePokemonDetails

    suspend fun getPokemonPreviews(
        nameFilter: String,
        typeFilter: String,
    ): List<RemotePokemonPreview>

    suspend fun getTypes(): List<PokemonType>
}