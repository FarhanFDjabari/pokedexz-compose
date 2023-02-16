package com.example.pokedexz.ui.features.caught

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexz.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaughtPokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _state = mutableStateOf(PokeballState())
    val pokeballState : State<PokeballState> = _state

    private var getCaughtPokemonJob : Job? = null

    init {
        getAllCaughtPokemon()
    }

    fun onEvent(event: PokeballEvent) {
        when (event) {
            is PokeballEvent.Release -> {
                viewModelScope.launch {
                    repository.releasePokemon(event.pokemon)
                }
            }
        }
    }

    private fun getAllCaughtPokemon() {
        getCaughtPokemonJob?.cancel()
        getCaughtPokemonJob = repository.getAllPokemon().onEach {
            _state.value = pokeballState.value.copy(
                pokemons = it
            )
        }.launchIn(viewModelScope)
    }
}