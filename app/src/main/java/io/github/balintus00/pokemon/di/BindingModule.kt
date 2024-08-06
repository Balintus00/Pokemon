package io.github.balintus00.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.balintus00.pokemon.data.disk.RoomPersistentPokemonDataSource
import io.github.balintus00.pokemon.data.network.KtorRemotePokemonDataSource
import io.github.balintus00.pokemon.repository.DefaultPokemonRepository
import io.github.balintus00.pokemon.repository.PokemonRepository
import io.github.balintus00.pokemon.repository.data.PersistentPokemonDataSource
import io.github.balintus00.pokemon.repository.data.RemotePokemonDataSource

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class BindingModule {

    @Binds
    internal abstract fun bindPokemonRepository(
        defaultPokemonRepository: DefaultPokemonRepository,
    ): PokemonRepository

    @Binds
    internal abstract fun bindPersistentPokemonDataSource(
        roomPersistentPokemonDataSource: RoomPersistentPokemonDataSource,
    ) : PersistentPokemonDataSource

    @Binds
    internal abstract fun bindRemotePokemonDataSource(
        ktorRemotePokemonDataSource: KtorRemotePokemonDataSource,
    ) : RemotePokemonDataSource
}