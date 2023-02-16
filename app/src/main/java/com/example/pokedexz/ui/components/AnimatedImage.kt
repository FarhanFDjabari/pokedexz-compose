package com.example.pokedexz.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun AnimatedImage(
    modifier: Modifier = Modifier,
    @DrawableRes drawableInt: Int,
    duration: Int = 30,
    tint: Color = MaterialTheme.colors.background,
    easing: Easing = EaseOutBounce,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scaleInfinite by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = .85f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(id = drawableInt),
        contentDescription = "Animated Image",
        modifier = modifier
            .graphicsLayer {
                scaleX = scaleInfinite
                scaleY = scaleInfinite
                rotationZ = rotation
            },
        colorFilter = ColorFilter.tint(tint),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}