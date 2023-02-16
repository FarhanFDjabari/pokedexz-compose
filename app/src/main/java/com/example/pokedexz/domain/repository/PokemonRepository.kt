package com.example.pokedexz.domain.repository

import com.example.pokedexz.data.models.PokedexListEntry
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.data.remote.response.PokemonList
import com.example.pokedexz.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList>
    suspend fun getPokemonDetail(pokemonName: String) : Resource<Pokemon>
    fun getAllPokemon() : Flow<List<PokedexListEntry>>
    fun getPokemon(name: String) : Flow<PokedexListEntry?>
    suspend fun catchPokemon(pokemon: PokedexListEntry)
    suspend fun releasePokemon(pokemon: PokedexListEntry)
}