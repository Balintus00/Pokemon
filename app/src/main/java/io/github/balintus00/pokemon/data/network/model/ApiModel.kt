package io.github.balintus00.pokemon.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO create separate class file for each model

@Serializable
data class PlainPokemonQueryResponse(val results: List<PokemonApiResourceReference>)

@Serializable
data class PokemonApiResourceReference(val name: String, val url: String)

@Serializable
data class PokemonApiModel(
    val abilities: List<PokemonAbilityApiModel>,
    val height: Int,
    val sprites: PokemonSpritesApiModel,
    val types: List<PokemonTypeApiModel>,
    val weight: Int,
)

@Serializable
data class PokemonAbilityApiModel(
    @SerialName("ability") val resourceReference: PokemonApiResourceReference,
    @SerialName("is_hidden") val isHidden: Boolean,
)

@Serializable
data class PokemonSpritesApiModel(@SerialName("front_default") val defaultUrl: String)

@Serializable
data class PokemonTypeApiModel(
    @SerialName("type") val resourceReference: PokemonApiResourceReference,
)

@Serializable
data class PokemonFilterByTypeQueryResponse(
    @SerialName("pokemon") val pokemons: List<PokemonReferenceApiModel>,
)

@Serializable
data class PokemonReferenceApiModel(val pokemon: PokemonApiResourceReference)