package com.example.pokedexz.ui.features.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexz.R
import com.example.pokedexz.ui.components.PokeballLoadingLight
import com.example.pokedexz.ui.components.PokeballLoadingNight
import com.example.pokedexz.ui.theme.PokedexZTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
            contentDescription = "splash logo",
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isSystemInDarkTheme()) {
            PokeballLoadingNight(
                easing = EaseInOut
            )
        } else {
            PokeballLoadingLight(
                easing = EaseInOut
            )
        }

        LaunchedEffect(key1 = true) {
            delay(1250)
            navController.navigate("pokemon_list_screen") {
                popUpTo("splash_screen") {
                    inclusive = true
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenLightPreview() {
    PokedexZTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SplashScreen(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenDarkPreview() {
    PokedexZTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SplashScreen(navController = rememberNavController())
        }
    }
}