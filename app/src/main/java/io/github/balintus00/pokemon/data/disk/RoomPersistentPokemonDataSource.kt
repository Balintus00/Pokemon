package io.github.balintus00.pokemon.data.disk

import io.github.balintus00.pokemon.repository.data.PersistentPokemonDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomPersistentPokemonDataSource @Inject constructor(
    private val caughtPokemonDao: CaughtPokemonDao,
) : PersistentPokemonDataSource {

    override fun getCaughtPokemonNames(): Flow<List<String>> =
        caughtPokemonDao.getCaughtPokemons().map { list -> list.map { it.name } }

    override fun getCaughtPokemonNameByName(name: String): Flow<String?> =
        caughtPokemonDao.getCaughtPokemonByName(name).map { it?.name }

    override suspend fun saveCaughtPokemon(pokemonName: String) {
        caughtPokemonDao.saveCaughtPokemon(CaughtPokemon(pokemonName))
    }

    override suspend fun deleteCaughtPokemon(pokemonName: String) {
        caughtPokemonDao.deleteCaughtPokemon(pokemonName)
    }
}