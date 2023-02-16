package com.example.pokedexz.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokeball")
data class PokedexListEntry(
    val pokemonName: String,
    val imageUrl: String,
    val number: Int,
    @PrimaryKey
    val id: Int = 0,
)
