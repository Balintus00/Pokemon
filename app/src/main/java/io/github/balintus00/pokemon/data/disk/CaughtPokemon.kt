package io.github.balintus00.pokemon.data.disk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaughtPokemon(@PrimaryKey val name: String)