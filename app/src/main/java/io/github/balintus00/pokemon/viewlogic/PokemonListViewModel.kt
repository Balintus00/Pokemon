package io.github.balintus00.pokemon.viewlogic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.balintus00.pokemon.repository.DefaultPokemonRepository
import io.github.balintus00.pokemon.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: DefaultPokemonRepository,   // TODO
) : ViewModel()