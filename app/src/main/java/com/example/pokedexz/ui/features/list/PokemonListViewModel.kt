package com.example.pokedexz.ui.features.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedexz.data.models.PokedexListEntry
import com.example.pokedexz.domain.repository.PokemonRepository
import com.example.pokedexz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isSearchNotFound = mutableStateOf(false)

    private var tempSearchlist = listOf<PokedexListEntry>()
    private var isFirstTimeSearch = true
    var isSearching = mutableStateOf(false)

    init {
        getPokemonList()
    }

    fun searchPokemon(query: String) {
        val listToSearch = if (isFirstTimeSearch) pokemonList.value else tempSearchlist
        viewModelScope.launch(Dispatchers.Default) {
            isSearching.value = true

            if (query.isEmpty()) {
                pokemonList.value = listToSearch
                isFirstTimeSearch = true
                isSearching.value = false
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), true)
            }

            isSearchNotFound.value = results.isEmpty()

            if (isFirstTimeSearch) {
                tempSearchlist = pokemonList.value
                isFirstTimeSearch = false
            }

            pokemonList.value = results
        }
    }

    fun getPokemonList() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                when (val result = repository.getPokemonList(20, currentPage * 20)) {
                    is Resource.Success -> {
                        endReached.value = currentPage * 20 >= (result.data?.count ?: 0)
                        val pokedexEntries = result.data?.results?.mapIndexed { index, entry ->
                            val number = if (entry.url.endsWith("/")) {
                                entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                entry.url.takeLastWhile { it.isDigit() }
                            }
                            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
                            PokedexListEntry(
                                pokemonName = entry.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                },
                                imageUrl = imageUrl,
                                number = number.toInt()
                            )
                        }
                        currentPage++
                        loadError.value = ""
                        isLoading.value = false
                        pokemonList.value += pokedexEntries?: listOf()
                    }
                    is Resource.Error -> {
                        loadError.value = "${result.message}"
                        isLoading.value = false
                    }
                    else -> {
                        isLoading.value = true
                    }
                }
            } catch (e: Exception) {
                loadError.value = "${e.message}"
            }
        }
    }

    fun calculateDominantColor(drawable: Drawable, onFinished: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate {
            it?.dominantSwatch?.rgb?.let { colorValue ->
                onFinished(Color(colorValue))
            }
        }
    }
}