package com.example.pokedexz.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexz.R
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokeballLoadingLight(
    modifier: Modifier = Modifier,
    duration: Int = 1000,
    easing: Easing = EaseOutBounce) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = easing
            ),
        )
    )

    Image(
        painter = painterResource(id = R.drawable.pokeball_loading_light),
        contentDescription = "Loading",
        modifier = modifier
            .sizeIn(
                minWidth = 50.dp,
                maxWidth = 80.dp,
                minHeight = 50.dp,
                maxHeight = 80.dp
            )
            .rotate(angle),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}

@Composable
fun PokeballLoadingNight(
    modifier: Modifier = Modifier,
    duration: Int = 1000,
    easing: Easing = EaseOutBounce,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = easing
            ),
        )
    )

    Image(
        painter = painterResource(id = R.drawable.pokeball_loading_night),
        contentDescription = "Loading",
        modifier = modifier
            .sizeIn(
                minWidth = 50.dp,
                maxWidth = 80.dp,
                minHeight = 50.dp,
                maxHeight = 80.dp
            )
            .rotate(angle),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}

@Preview(showBackground = true)
@Composable
fun PokeballLoadingLightPreview() {
    PokedexZTheme {
        PokeballLoadingLight()
    }
}

@Preview(showBackground = true)
@Composable
fun PokeballLoadingDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        PokeballLoadingNight()
    }
}
