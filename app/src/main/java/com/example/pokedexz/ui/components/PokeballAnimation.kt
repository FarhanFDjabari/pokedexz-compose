package com.example.pokedexz.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.pokedexz.R
import com.example.pokedexz.ui.theme.PokedexZTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun CapturePokemonAnimation(
    modifier: Modifier = Modifier,
    drawable: Drawable? = null,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.pokeball_lottie)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 3f,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true,
        cancellationBehavior = LottieCancellationBehavior.Immediately,
    )

    val infiniteTransition = rememberInfiniteTransition()

    val opacity by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
        )
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberDrawablePainter(drawable = drawable),
            contentDescription = "pokemon",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .size(200.dp)
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(150.dp)
                .scale(scale)
                .alpha(opacity)
        )
    }
}

@Composable
fun ReleasePokemonAnimation(
    modifier: Modifier = Modifier,
    drawable: Drawable? = null,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.pokeball_lottie)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 3f,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true,
        cancellationBehavior = LottieCancellationBehavior.Immediately,
    )

    val infiniteTransition = rememberInfiniteTransition()

    val opacity by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            ),
        )
    )

    val imageOpacity by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 750,
                easing = LinearEasing,
                delayMillis = 750
            ),
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing,
            ),
        )
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberDrawablePainter(drawable = drawable),
            contentDescription = "pokemon",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .size(200.dp)
                .alpha(imageOpacity)
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(150.dp)
                .scale(scale)
                .alpha(opacity)
        )
    }
}

@Preview
@Composable
fun CapturePokemonAnimationPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CapturePokemonAnimation()
        }
    }
}

@Preview
@Composable
fun ReleasePokemonAnimationPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ReleasePokemonAnimation()
        }
    }
}