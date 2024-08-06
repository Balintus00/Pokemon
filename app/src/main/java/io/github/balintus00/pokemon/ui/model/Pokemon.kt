package io.github.balintus00.pokemon.ui.model

data class Pokemon(
    val name: String,
    val abilities: Set<String>,
    val imageUrl: String,
    val isCaught: Boolean,
    val height: Int,
    val type: String,
    val weight: Int,
    val isCaughtStateLoading: Boolean = false,
)