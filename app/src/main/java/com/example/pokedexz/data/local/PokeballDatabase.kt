package com.example.pokedexz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedexz.data.models.PokedexListEntry

@Database(
    entities = [PokedexListEntry::class],
    version = 1
)
abstract class PokeballDatabase : RoomDatabase() {
    abstract val pokeballDao: PokeballDao

    companion object {
        const val DATABASE_NAME = "pokeball_db"
    }
}