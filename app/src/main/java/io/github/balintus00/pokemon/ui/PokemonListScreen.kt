package io.github.balintus00.pokemon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.balintus00.pokemon.viewlogic.PokemonListViewModel

@Composable
internal fun PokemonListScreen(
    modifier: Modifier = Modifier,
    navigateToDetailAction: (pokemonID: String) -> Unit = {},
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    Column {
        Text("Pokemon List")
        Button(onClick = { navigateToDetailAction("1") }) {
            Text("Navigate")
        }
    }
}