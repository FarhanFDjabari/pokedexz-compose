package com.example.pokedexz.ui.features.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexz.R
import com.example.pokedexz.ui.features.list.components.PokedexGrid
import com.example.pokedexz.ui.features.list.components.PokedexSearchBar
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null
        ) {
            focusManager.clearFocus()
        },
        topBar = {},
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                    contentDescription = "Pokemon Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                PokedexSearchBar(
                    hint = "Search pokemon",
                    focusManager = focusManager,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) { query ->
                    viewModel.searchPokemon(query)
                }
                PokedexGrid(
                    navController = navController,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("caught_pokemon_screen")
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.background,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.open_pokeball_icon),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colors.background,
                    ),
                    modifier = Modifier
                        .size(65.dp),
                    contentDescription = "View Caught Pokemon"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    PokedexZTheme {
        PokemonListScreen(
            navController = rememberNavController(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreviewDark() {
    PokedexZTheme(darkTheme = true) {
        PokemonListScreen(
            navController = rememberNavController(),
        )
    }
}