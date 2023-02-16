package com.example.pokedexz.di

import android.app.Application
import androidx.room.Room
import com.example.pokedexz.data.remote.PokeApi
import com.example.pokedexz.domain.repository.PokemonRepository
import com.example.pokedexz.data.PokemonRepositoryImpl
import com.example.pokedexz.data.local.PokeballDatabase
import com.example.pokedexz.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi,
        db: PokeballDatabase
    ) : PokemonRepository = PokemonRepositoryImpl(api, db.pokeballDao)

    @Singleton
    @Provides
    fun providePokeApi() : PokeApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(PokeApi::class.java)

    @Singleton
    @Provides
    fun providePokeballDatabase(app: Application) : PokeballDatabase {
        return Room.databaseBuilder(
            app,
            PokeballDatabase::class.java,
            PokeballDatabase.DATABASE_NAME
        ).build()
    }
}