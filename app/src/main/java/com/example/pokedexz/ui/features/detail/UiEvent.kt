package com.example.pokedexz.ui.features.detail

import com.example.pokedexz.data.remote.response.Pokemon

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    data class ShowCaptureDialog(val pokemon: Pokemon): UiEvent()
    data class ShowReleaseDialog(val pokemon: Pokemon): UiEvent()
    object CapturePokemon: UiEvent()
    object ReleasePokemon: UiEvent()
}
