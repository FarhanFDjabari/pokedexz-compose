package com.example.pokedexz.ui.features.detail

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.ui.features.detail.components.PokemonDetailStateWrapper
import com.example.pokedexz.ui.features.detail.components.PokemonDetailTopSection
import com.example.pokedexz.ui.theme.PokedexZTheme
import com.example.pokedexz.util.Resource

@Composable
fun PokemonDetailScreen(
    navController: NavController,
    pokemonName: String,
    dominantColor: Color,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonDetail(pokemonName)
    }.value

    val pokemonDrawable = remember {
        mutableStateOf<Drawable?>(null)
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(dominantColor)
                .padding(bottom = 16.dp)
        ) {
            PokemonDetailTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                pokemonImage = pokemonDrawable.value,
                scaffoldState = scaffoldState,
                dominantColor = dominantColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = (20 + 200 / 2f).dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                loadingModifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    )
            )
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (pokemonInfo is Resource.Success) {
                    pokemonInfo.data?.sprites?.let {sprite ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(sprite.front_default)
                                .crossfade(true)
                                .build(),
                            onSuccess = {painter ->
                                pokemonDrawable.value = painter.result.drawable
                            },
                            contentDescription = pokemonInfo.data.name,
                            modifier = Modifier
                                .size(200.dp)
                                .offset(y = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonDetailScreenLightPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PokemonDetailScreen(
                navController = rememberNavController(),
                pokemonName = "",
                dominantColor = MaterialTheme.colors.primary,
            )
        }
    }
}

@Preview
@Composable
fun PokemonDetailScreenDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PokemonDetailScreen(
                navController = rememberNavController(),
                pokemonName = "",
                dominantColor = MaterialTheme.colors.primary,
            )
        }
    }
}