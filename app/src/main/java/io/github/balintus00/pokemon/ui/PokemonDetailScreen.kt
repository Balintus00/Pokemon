package io.github.balintus00.pokemon.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.balintus00.pokemon.viewlogic.PokemonDetailViewModel

@Composable
internal fun PokemonDetailScreen(
    pokemonID: String,
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 30.dp),
    ) {
        AsyncImage(
            contentDescription = "TODO",
            contentScale = ContentScale.Crop,
            model = "https://24ai.tech/en/wp-content/uploads/sites/3/2023/10/01_product_1_sdelat-izobrazhenie-4-3-6-scaled.jpg",
            modifier = Modifier
                .border(5.dp, Color(255, 0, 0))
                .aspectRatio(1.2222f)
                .fillMaxWidth(),
        )

        PokemonLabeledAttribute(label = "Name", values = listOf("Pokemon1"))
        PokemonLabeledAttribute(label = "Weight", values = listOf("999kg"))
        PokemonLabeledAttribute(label = "Height", values = listOf("999m"))
        PokemonLabeledAttribute(
            label = "Abilities",
            values = listOf("Sadness", "Sadness", "Sadness")
        )
        PokemonLabeledAttribute(label = "Status", values = listOf("TODO"))

        Spacer(modifier = Modifier.weight(1.0f))

        Button(
            modifier = Modifier.fillMaxWidth().height(33.dp),
            onClick = { /*TODO*/ },
        ) {
            Text("Release")
        }
    }
}

@Composable
fun PokemonLabeledAttribute(
    label: String,
    values: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(0.5f),
            text = label,
        )

        Column(
            modifier = Modifier.weight(0.5f),
        ) {
            values.forEach { value ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = value
                )

            }
        }
    }
}