package com.example.pokedexz.ui.features.detail.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.pokedexz.ui.components.CapturePokemonAnimation
import com.example.pokedexz.ui.components.ReleasePokemonAnimation
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokeballProcessDialog(
    modifier: Modifier = Modifier,
    title: String,
    pokemonImage: Drawable? = null,
    isCapture: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        shape = RoundedCornerShape(15.dp),
        onDismissRequest = onDismiss,
        buttons = {},
        title = {
            if (isCapture) {
                CapturePokemonAnimation(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    drawable = pokemonImage
                )
            } else {
                ReleasePokemonAnimation(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    drawable = pokemonImage
                )
            }
        },
        text = {
            Text(
                title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Preview
@Composable
fun PokemonDialogLightPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PokeballProcessDialog(
                title = "Capture Squirtle"
            )
        }
    }
}

@Preview
@Composable
fun PokemonDialogDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PokeballProcessDialog(
                title = "Capture Squirtle"
            )
        }
    }
}