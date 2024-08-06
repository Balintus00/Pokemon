package io.github.balintus00.pokemon.repository.model

data class RemotePokemonDetails(
    val name: String,
    val abilities: Set<String>,
    val imageUrl: String,
    val height: Int,
    val type: String,
    val weight: Int,
)