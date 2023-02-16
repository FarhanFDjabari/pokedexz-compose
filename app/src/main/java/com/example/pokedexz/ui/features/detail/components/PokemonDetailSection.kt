package com.example.pokedexz.ui.features.detail.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedexz.R
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.ui.components.AnimatedImage
import java.util.*

@Composable
fun PokemonDetailSection(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    isAlreadyCaptured: Boolean = false,
    dominantColor: Color,
    onCaptureOrReleaseClick: (Pokemon) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 80.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemon.id} ${pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground
        )
        PokemonTypeSection(types = pokemon.types)
        PokemonDetailDataSection(pokemonWeight = pokemon.weight, pokemonHeight = pokemon.height)
        PokemonBaseStats(pokemon = pokemon, dominantColor = dominantColor)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                onCaptureOrReleaseClick(pokemon)
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.background
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        ) {
            AnimatedImage(
                drawableInt = if (isAlreadyCaptured)
                    R.drawable.open_pokeball_icon else R.drawable.pokeball_overlay_bg_frame,
                easing = LinearEasing,
                duration = if (isAlreadyCaptured) 1000 else 150,
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isAlreadyCaptured) "Release" else "Capture",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}