package io.github.balintus00.pokemon.repository

import io.github.balintus00.pokemon.repository.data.PersistentPokemonDataSource
import io.github.balintus00.pokemon.repository.data.RemotePokemonDataSource
import javax.inject.Inject

class DefaultPokemonRepository @Inject constructor(
    private val persistentPokemonDataSource: PersistentPokemonDataSource,
    private val remotePokemonDataSource: RemotePokemonDataSource,
) : PokemonRepository