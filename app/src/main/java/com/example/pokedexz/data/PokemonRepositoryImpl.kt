package com.example.pokedexz.data

import com.example.pokedexz.data.local.PokeballDao
import com.example.pokedexz.data.models.PokedexListEntry
import com.example.pokedexz.data.remote.PokeApi
import com.example.pokedexz.domain.repository.PokemonRepository
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.data.remote.response.PokemonList
import com.example.pokedexz.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi,
    private val dao: PokeballDao
) : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val result = try {
            api.getPokemonList(limit = limit, offset = offset)
        } catch (e: Exception) {
            return Resource.Error(message = "${e.message}")
        }
        return Resource.Success(data = result)
    }

    override suspend fun getPokemonDetail(pokemonName: String): Resource<Pokemon> {
        val result = try {
            api.getPokemonDetail(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(message = "${e.message}")
        }
        return Resource.Success(data = result)
    }

    override fun getAllPokemon(): Flow<List<PokedexListEntry>> {
        return dao.getAllPokemons()
    }

    override fun getPokemon(name: String): Flow<PokedexListEntry?> {
        return dao.getPokemon(name)
    }

    override suspend fun catchPokemon(pokemon: PokedexListEntry) {
        dao.catchPokemon(pokemon)
    }

    override suspend fun releasePokemon(pokemon: PokedexListEntry) {
        dao.releasePokemon(pokemon)
    }
}