package com.example.pokedexz.ui.features.caught.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedexz.data.models.PokedexListEntry
import com.example.pokedexz.ui.features.list.components.PokedexItem
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokeballGrid(
    modifier: Modifier = Modifier,
    pokemons: List<PokedexListEntry>,
    onItemClick: (Int, PokedexListEntry) -> Unit = {_, _ ->}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pokemons) {
            PokedexItem(entry = it) { dominantColor, element ->
                onItemClick(dominantColor, element)
            }
        }
    }
    if (pokemons.isEmpty()) {
        EmptyPokeballSection()
    }
}

@Composable
fun EmptyPokeballSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = com.example.pokedexz.R.drawable.pokeball_overlay_bg_frame),
            contentDescription = "empty pokeball icon",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .sizeIn(minWidth = 150.dp, maxHeight = 150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Your pokeball is empty, go catch some pokemon!",
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexGridLightPreview() {
    PokedexZTheme {
        PokeballGrid(
            pokemons = listOf(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexGridDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        PokeballGrid(
            pokemons = listOf(),
        )
    }
}