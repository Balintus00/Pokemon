package io.github.balintus00.pokemon.ui.model

data class PokemonPreview(
    val name: String,
    val isCaught: Boolean,
    val type: String,
    val isCaughtStateLoading: Boolean = false
)