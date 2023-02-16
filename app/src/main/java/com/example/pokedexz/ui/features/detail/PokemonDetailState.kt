package com.example.pokedexz.ui.features.detail

import com.example.pokedexz.data.remote.response.Pokemon

sealed class PokemonDetailState {
    data class CapturingProcess(val pokemon: Pokemon) : PokemonDetailState()
    data class CapturePokemon(val pokemon: Pokemon) : PokemonDetailState()
    data class ReleasingProcess(val pokemon: Pokemon) : PokemonDetailState()
    data class ReleasePokemon(val pokemon: Pokemon) : PokemonDetailState()
}
