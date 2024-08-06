package io.github.balintus00.pokemon.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import io.github.balintus00.pokemon.ui.theme.PokemonTheme
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PokemonTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            navigationIcon = {
                                val currentDestination by
                                    navController.currentBackStackEntryAsState()

                                val isDetailsDestination = try {
                                    currentDestination?.toRoute<PokemonDetailsDestination>()
                                        ?: false
                                    true
                                } catch (e: Exception) {
                                    false
                                }

                                if (isDetailsDestination) {
                                    IconButton(
                                        onClick = {
                                            navController.popBackStack<PokemonListDestination>(
                                                inclusive = false,
                                            )
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = "TODO",
                                        )
                                    }
                                } else {
                                    IconButton(
                                        onClick = {
                                            // TODO open menu
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "TODO",
                                        )
                                    }
                                }
                            },
                            title = {
                                Text("TODO")
                            },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = PokemonListDestination,
                    ) {
                        composable<PokemonListDestination> { _ ->
                            PokemonListScreen(
                                navigateToDetailAction = { pokemonID ->
                                    navController.navigate(
                                        route = PokemonDetailsDestination(pokemonID)
                                    )
                                }
                            )
                        }

                        composable<PokemonDetailsDestination>(
                        ) { navBackStackEntry ->
                            val destination: PokemonDetailsDestination = navBackStackEntry.toRoute()

                            PokemonDetailScreen(
                                pokemonID = destination.id,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object PokemonListDestination

@Serializable
data class PokemonDetailsDestination(val id: String)