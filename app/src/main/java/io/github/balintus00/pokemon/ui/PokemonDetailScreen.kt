package io.github.balintus00.pokemon.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.balintus00.pokemon.viewlogic.PokemonDetailViewModel

@Composable
internal fun PokemonDetailScreen(
    pokemonID: String,
    modifier: Modifier = Modifier,
    navigateBackAction: () -> Unit = {},
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    Text("Pokemon: $pokemonID")
}