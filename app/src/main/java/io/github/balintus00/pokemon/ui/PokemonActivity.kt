package io.github.balintus00.pokemon.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import io.github.balintus00.pokemon.R
import io.github.balintus00.pokemon.ui.theme.PokemonTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PokemonTheme {
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
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
                                            scope.launch {
                                                if (drawerState.isOpen) {
                                                    drawerState.close()
                                                } else {
                                                    drawerState.open()
                                                }
                                            }
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
                                Image(
                                    contentDescription = null,
                                    modifier = Modifier.width(101.dp).height(37.dp),
                                    painter = painterResource(id = R.drawable.ic_pokemon),
                                )
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
                            ModalNavigationDrawer(
                                drawerContent = {
                                    ModalDrawerSheet(
                                        drawerShape = RoundedCornerShape(0.dp),
                                        modifier = Modifier.width(261.dp),
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            text = "PokeAPI Documentation",
                                        )
                                    }
                                },
                                drawerState = drawerState,
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                PokemonListScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    navigateToDetailAction = { pokemonID ->
                                        navController.navigate(
                                            route = PokemonDetailsDestination(pokemonID)
                                        )
                                    }
                                )
                            }
                        }

                        composable<PokemonDetailsDestination>(
                        ) { navBackStackEntry ->
                            val destination: PokemonDetailsDestination = navBackStackEntry.toRoute()

                            PokemonDetailScreen(
                                modifier = Modifier.fillMaxSize(),
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