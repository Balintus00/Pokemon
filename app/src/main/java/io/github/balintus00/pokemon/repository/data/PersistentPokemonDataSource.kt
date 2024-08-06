package io.github.balintus00.pokemon.repository.data

import kotlinx.coroutines.flow.Flow

interface PersistentPokemonDataSource {

    fun getCaughtPokemonNames(): Flow<List<String>>

    fun getCaughtPokemonNameByName(name: String): Flow<String?>

    suspend fun saveCaughtPokemon(pokemonName: String)

    suspend fun deleteCaughtPokemon(pokemonName: String)
}