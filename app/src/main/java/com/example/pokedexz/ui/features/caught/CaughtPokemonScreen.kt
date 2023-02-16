package com.example.pokedexz.ui.features.caught

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexz.R
import com.example.pokedexz.ui.features.caught.components.PokeballGrid
import com.example.pokedexz.ui.theme.PokedexZTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@Composable
fun CaughtPokemonScreen(
    navController: NavController,
    viewModel: CaughtPokemonViewModel = hiltViewModel()
) {
    val state = viewModel.pokeballState.value
    val scope = rememberCoroutineScope()
    val rotation = remember {
        Animatable(0f)
    }
    val opacity = remember {
        Animatable(1f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    awaitAll(
                        async {
                            rotation.animateTo(
                                180f,
                                animationSpec = tween(150, easing = EaseInOut)
                            )
                        },
                        async {
                            opacity.animateTo(
                                0.5f,
                                animationSpec = tween(100, easing = EaseInOut)
                            )
                        }
                    )
                    navController.popBackStack()
                }
            }
        ) {
            Image(
                painter = painterResource(
                    id = if (isSystemInDarkTheme()) R.drawable.pokeball_loading_night
                    else R.drawable.pokeball_loading_light
                ),
                contentDescription = "Pokeball Logo",
                modifier = Modifier
                    .size(80.dp)
                    .rotate(rotation.value)
                    .alpha(opacity.value)
            )
        }
        PokeballGrid(
            pokemons = state.pokemons,
        ) { dominantColor, element ->
            navController.navigate("pokemon_detail_screen/${element.pokemonName}?dominantColor=$dominantColor")
        }
    }
}

@Preview
@Composable
fun CaughtPokemonScreenLightPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CaughtPokemonScreen(navController = rememberNavController())
        }
    }
}

@Preview
@Composable
fun CaughtPokemonScreenDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CaughtPokemonScreen(navController = rememberNavController())
        }
    }
}