package com.example.pokedexz.ui.features.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexz.ui.components.PokeballLoadingLight
import com.example.pokedexz.ui.components.PokeballLoadingNight
import com.example.pokedexz.ui.features.list.PokemonListViewModel
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokedexGrid(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val pokemonList = remember { viewModel.pokemonList }
    val endReached = remember { viewModel.endReached }
    val loadError = remember { viewModel.loadError }
    val isLoading = remember { viewModel.isLoading }
    val isSearching = remember { viewModel.isSearching }
    val searchNotFound = remember { viewModel.isSearchNotFound }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(pokemonList.value) { index, entry ->
            if (index >= pokemonList.value.size - 1 && !endReached.value &&
                !isLoading.value && !isSearching.value) {
                LaunchedEffect(key1 = true) {
                    viewModel.getPokemonList()
                }
            }
            PokedexItem(
                entry = entry,
            ) { dominantColor, element ->
                navController.navigate(
                    "pokemon_detail_screen/${element.pokemonName.lowercase()}?dominantColor=$dominantColor"
                )
            }
        }
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading.value) {
            if (isSystemInDarkTheme()) {
                PokeballLoadingNight(
                    modifier = modifier
                        .sizeIn(
                            maxWidth = 100.dp,
                            maxHeight = 100.dp
                        )
                        .padding(32.dp)
                )
            } else {
                PokeballLoadingLight(
                    modifier = modifier
                        .sizeIn(
                            maxWidth = 100.dp,
                            maxHeight = 100.dp
                        )
                        .padding(32.dp)
                )
            }
        }
        if (searchNotFound.value) {
            NotFoundSection()
        }
        if (loadError.value.isNotEmpty()) {
            RetrySection(error = loadError.value) {
                viewModel.getPokemonList()
            }
        }
    }
}

@Composable
fun NotFoundSection() {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = com.example.pokedexz.R.drawable.pikachu_placeholder_icon),
            contentDescription = "not found icon",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .sizeIn(minWidth = 150.dp, maxHeight = 150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Pokemon Not Found",
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            error,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Retry", color = MaterialTheme.colors.background)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexGridLightPreview() {
    PokedexZTheme {
        PokedexGrid(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexGridDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        PokedexGrid(
            navController = rememberNavController()
        )
    }
}