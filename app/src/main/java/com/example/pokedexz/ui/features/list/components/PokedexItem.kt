package com.example.pokedexz.ui.features.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedexz.R
import com.example.pokedexz.data.models.PokedexListEntry
import com.example.pokedexz.ui.features.list.PokemonListViewModel
import com.example.pokedexz.ui.theme.PokedexZTheme
import com.example.pokedexz.ui.theme.RobotoCondensed

@Composable
fun PokedexItem(
    entry: PokedexListEntry,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onItemClick: (Int, PokedexListEntry) -> Unit = { _, _ -> },
) {
    val defaultDominantColor = MaterialTheme.colors.background
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    ),
                    start = Offset(Float.POSITIVE_INFINITY, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .clickable {
                onItemClick(dominantColor.toArgb(), entry)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokeball_overlay_bg_frame),
            contentScale = ContentScale.Fit,
            contentDescription = "icon overlay",
            alpha = 0.25f,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 300.dp),
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                onSuccess = {
                    viewModel.calculateDominantColor(it.result.drawable) { color ->
                        dominantColor = color
                    }
                },
                placeholder = painterResource(id = R.drawable.pokedex_icon_placeholder),
                contentDescription = entry.pokemonName,
                contentScale = ContentScale.Fit,
                error = painterResource(id = R.drawable.pokedex_icon_placeholder),
                modifier = Modifier
                    .sizeIn(
                        minWidth = 120.dp,
                        minHeight = 120.dp,
                        maxWidth = 150.dp,
                        maxHeight = 150.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonEntityLightPreview() {
    PokedexZTheme {
        PokedexItem(
            PokedexListEntry(
                pokemonName = "name",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png",
                number = 10,
            ),
            Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonEntityDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        PokedexItem(
            PokedexListEntry(
                pokemonName = "name",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png",
                number = 10,
            ),
            Modifier,
        )
    }
}