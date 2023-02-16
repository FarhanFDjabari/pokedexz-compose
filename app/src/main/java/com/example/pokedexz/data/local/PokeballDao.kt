package com.example.pokedexz.data.local

import androidx.room.*
import com.example.pokedexz.data.models.PokedexListEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeballDao {
    @Query("SELECT * FROM pokeball")
    fun getAllPokemons() : Flow<List<PokedexListEntry>>

    @Query("SELECT * FROM pokeball WHERE pokemonName = :name LIMIT 1")
    fun getPokemon(name: String) : Flow<PokedexListEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun catchPokemon(pokemon: PokedexListEntry)

    @Delete
    fun releasePokemon(pokemon: PokedexListEntry)
}