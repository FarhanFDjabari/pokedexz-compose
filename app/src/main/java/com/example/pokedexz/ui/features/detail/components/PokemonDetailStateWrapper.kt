package com.example.pokedexz.ui.features.detail.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.ui.components.PokeballLoadingLight
import com.example.pokedexz.ui.components.PokeballLoadingNight
import com.example.pokedexz.ui.features.detail.PokemonDetailState
import com.example.pokedexz.ui.features.detail.PokemonDetailViewModel
import com.example.pokedexz.ui.features.detail.UiEvent
import com.example.pokedexz.util.Resource
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun PokemonDetailStateWrapper(
    modifier: Modifier = Modifier,
    pokemonInfo: Resource<Pokemon>,
    pokemonImage: Drawable? = null,
    dominantColor: Color,
    loadingModifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val isAlreadyCaptured = remember {
        viewModel.isAlreadyCaptured
    }.value

    val isOpenDialog = remember {
        mutableStateOf(false)
    }

    val isCapturePokemon = remember {
        mutableStateOf(false)
    }

    val dialogTitle = remember {
        mutableStateOf("")
    }

    if (isOpenDialog.value) {
        PokeballProcessDialog(
            title = dialogTitle.value,
            pokemonImage = pokemonImage,
            isCapture = isCapturePokemon.value,
            onDismiss = {
                isOpenDialog.value = false
            }
        )

    }
    
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowCaptureDialog -> {
                    val pokemonName = event.pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                    dialogTitle.value = "Catching $pokemonName"
                    isCapturePokemon.value = true
                    isOpenDialog.value = true
                    viewModel.onEvent(PokemonDetailState.CapturePokemon(event.pokemon))
                }
                is UiEvent.ShowReleaseDialog -> {
                    val pokemonName = event.pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                    isCapturePokemon.value = false
                    dialogTitle.value = "Releasing $pokemonName"
                    isOpenDialog.value = true
                    viewModel.onEvent(PokemonDetailState.ReleasePokemon(event.pokemon))
                }
                is UiEvent.CapturePokemon -> {
                    isOpenDialog.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Pokemon captured successfully!!!"
                    )
                }
                is UiEvent.ReleasePokemon -> {
                    isOpenDialog.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Pokemon has released"
                    )
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    when (pokemonInfo) {
        is Resource.Loading -> {
            if (isSystemInDarkTheme()) {
                PokeballLoadingNight(
                    modifier = loadingModifier
                )
            } else {
                PokeballLoadingLight(
                    modifier = loadingModifier
                )
            }
        }
        is Resource.Success -> {
            pokemonInfo.data?.let {
                PokemonDetailSection(
                    pokemon = it,
                    modifier = modifier.offset(y = (-20).dp),
                    dominantColor = dominantColor,
                    isAlreadyCaptured = isAlreadyCaptured,
                    onCaptureOrReleaseClick = {pokemon ->
                        if (isAlreadyCaptured) {
                            viewModel.onEvent(PokemonDetailState.ReleasingProcess(pokemon))
                        } else {
                            viewModel.onEvent(PokemonDetailState.CapturingProcess(pokemon))
                        }
                    },
                )
            }
        }
        is Resource.Error -> {
            Text(
                text = "${pokemonInfo.message}",
                color = MaterialTheme.colors.onBackground,
                modifier = modifier
            )
        }
    }
}
