package com.example.pokedexz.ui.features.detail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.util.parseStatToAbbr

@Composable
fun PokemonBaseStats(
    pokemon: Pokemon,
    dominantColor: Color,
    animDelayPerItem: Int = 100
) {
    val maxStatValue = remember {
        pokemon.stats.maxOf { it.base_stat }
    }
    Spacer(modifier = Modifier.height(8.dp))
    for (statIndex in pokemon.stats.indices) {
        val stat = pokemon.stats[statIndex]
        PokemonStat(
            statName = parseStatToAbbr(stat),
            statValue = stat.base_stat,
            statMaxValue = maxStatValue,
            height = 28.dp,
            statColor = dominantColor,
            animDelay = statIndex * animDelayPerItem,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}