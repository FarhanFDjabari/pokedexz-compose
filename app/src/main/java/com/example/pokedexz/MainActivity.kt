package com.example.pokedexz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedexz.ui.features.caught.CaughtPokemonScreen
import com.example.pokedexz.ui.features.detail.PokemonDetailScreen
import com.example.pokedexz.ui.features.list.PokemonListScreen
import com.example.pokedexz.ui.features.splash.SplashScreen
import com.example.pokedexz.ui.theme.PokedexZTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            PokedexZTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen"
                    ) {
                        composable(
                            "splash_screen",
                        ) {
                            SplashScreen(navController = navController)
                        }
                        composable(
                            "pokemon_list_screen",
                        ) {
                            PokemonListScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            "pokemon_detail_screen/{pokemonName}?dominantColor={dominantColor}",
                            arguments = listOf(
                                navArgument("dominantColor") {
                                    type = NavType.IntType
                                },
                                navArgument("pokemonName") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val dominantColor = remember {
                                val color = it.arguments?.getInt("dominantColor")
                                color?.let { Color(it) } ?: Color.White
                            }
                            val pokemonName = remember {
                                it.arguments?.getString("pokemonName")
                            }
                            PokemonDetailScreen(
                                navController = navController,
                                pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                                dominantColor = dominantColor
                            )
                        }
                        composable(
                            "caught_pokemon_screen"
                        ) {
                            CaughtPokemonScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}