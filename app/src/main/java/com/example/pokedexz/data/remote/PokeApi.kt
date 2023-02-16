package com.example.pokedexz.data.remote

import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.data.remote.response.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 20
    ) : PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") pokemonName: String
    ) : Pokemon
}