package io.github.balintus00.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.balintus00.pokemon.ui.model.PokemonPreview
import io.github.balintus00.pokemon.viewlogic.PokemonListViewModel

@Composable
internal fun PokemonListScreen(
    modifier: Modifier = Modifier,
    navigateToDetailAction: (pokemonID: String) -> Unit = {},
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    var filterCaught by remember { mutableStateOf(false) }
    var nameFilter by remember { mutableStateOf("") }
    var selectedType: String? by remember { mutableStateOf(null) }
    val types by remember { mutableStateOf(listOf("Fire", "Ice")) }

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            PokemonNameFilterTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 23.dp)
                ,
                onValueChange = { nameFilter = it },
                value = nameFilter,
            )
        }

        item {
            PokemonTypeFilterTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 26.dp),
                onOptionSelected = { selectedType = it },
                options = types,
                selectedOption = selectedType ?: "",
            )
        }

        item {
            PokemonCaughtFilterCheckbox(
                isChecked = filterCaught,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 18.dp),
                onCheckedChange = { filterCaught = !filterCaught }
            )
        }

        item {
            PokemonListHeader(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(Color(red = 255, green = 0, blue = 0))  // TODO color
                    .padding(start = 35.dp, top = 23.dp, bottom = 26.dp)
                    .fillMaxWidth(),
            )
        }

        items(
            // TODO
            listOf(
                PokemonPreview("Todomon1", true, "Fire"),
                PokemonPreview("Todomon2", false, "Ice"),
                PokemonPreview("Todomon3", true, "Fire"),
            ),
            key = { it.name },
        ) { pokemon ->
            PokemonListItem(
                changeCaughtStatusAction = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                onPokemonSelected = navigateToDetailAction,
                pokemon = pokemon,
            )
        }

        // TODO color remaining space
    }
}

@Composable
fun PokemonNameFilterTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    TextField(
        leadingIcon = {
            Icon(
                contentDescription = "TODO",
                imageVector = Icons.Default.Search,
            )
        },
        modifier = modifier,
        onValueChange = onValueChange,
        singleLine = true,
        value = value,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PokemonTypeFilterTextField(
    options: List<String>,
    selectedOption: String,
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        modifier = modifier,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            value = selectedOption,
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)

                        isExpanded = false
                    },
                    text = { Text(option) },
                )
            }
        }
    }
}

@Composable
fun PokemonCaughtFilterCheckbox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Pokemon Types",
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    onValueChange = { onCheckedChange(!isChecked) },
                    role = Role.Checkbox,
                    value = isChecked,
                ),
        ) {
            Checkbox(checked = isChecked, onCheckedChange = null)
            Text("Only show caught Pokemon")
        }
    }
}

@Composable
fun PokemonListHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(end = 67.dp),
            text = "Name",
        )
        Text(
            modifier = Modifier.padding(end = 41.dp),
            text = "Type",
        )
        Text(
            text = "Status",
        )
    }
}

@Composable
fun PokemonListItem(
    pokemon: PokemonPreview,
    changeCaughtStatusAction: () -> Unit = {},
    modifier: Modifier = Modifier,
    onPokemonSelected: (String) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
            .background(Color(red = 255, green = 0, blue = 0))
            .padding(vertical = 15.dp),
    ) {
        Card(
            modifier = Modifier
                .height(31.dp)
                .weight(1.0f)
                .clickable { onPokemonSelected(pokemon.name) },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.width(100.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = pokemon.name,
                )
                Text(
                    modifier = Modifier.width(68.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = pokemon.type,
                )
                Text(
                    modifier = Modifier.weight(1.0f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = if (pokemon.isCaught) "Caught" else "-",
                )
            }
        }

        Button(
            colors = if (pokemon.isCaught) {
                ButtonDefaults.buttonColors()
            } else {
                ButtonDefaults.buttonColors()
            },
            modifier = Modifier
                .width(76.dp)
                .height(31.dp),
            onClick = changeCaughtStatusAction,
            shape = RoundedCornerShape(7.dp),
        ) {
            Text(
                text = if (pokemon.isCaught) {
                    "Release"
                } else {
                    "Catch"
                }
            )
        }
    }
}