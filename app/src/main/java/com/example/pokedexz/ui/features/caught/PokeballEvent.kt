package com.example.pokedexz.ui.features.caught

import com.example.pokedexz.data.models.PokedexListEntry

sealed class PokeballEvent {
    data class Release(val pokemon: PokedexListEntry) : PokeballEvent()
}
