package com.example.pokedexz.ui.features.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexz.data.remote.response.Pokemon
import com.example.pokedexz.data.remote.response.toListEntry
import com.example.pokedexz.domain.repository.PokemonRepository
import com.example.pokedexz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _isAlreadyCaptured = mutableStateOf(false)
    val isAlreadyCaptured : State<Boolean> = _isAlreadyCaptured

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var checkCapturePokemonJob : Job? = null

    init {
        savedStateHandle.get<String>("pokemonName")?.let {
            checkIsPokemonCapturedBefore(it)
        }
    }

    suspend fun getPokemonDetail(name: String) : Resource<Pokemon> {
        return repository.getPokemonDetail(name)
    }

    private suspend fun catchPokemon(pokemon: Pokemon) {
        val pokedexEntry = pokemon.toListEntry()
        repository.catchPokemon(pokedexEntry)
    }

    private suspend fun releasePokemon(pokemon: Pokemon) {
        val pokedexEntry = pokemon.toListEntry()
        repository.releasePokemon(pokedexEntry)
    }

    fun onEvent(event: PokemonDetailState) {
        when (event) {
            is PokemonDetailState.CapturingProcess -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowCaptureDialog(
                        event.pokemon
                    ))
                }
            }
            is PokemonDetailState.CapturePokemon -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        catchPokemon(event.pokemon)
                        delay(2000)
                        _isAlreadyCaptured.value = true
                        _eventFlow.emit(UiEvent.CapturePokemon)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            e.message?: "Couldn't capture pokemon"
                        ))
                    }
                }
            }
            is PokemonDetailState.ReleasePokemon -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        releasePokemon(event.pokemon)
                        delay(2000)
                        _isAlreadyCaptured.value = false
                        _eventFlow.emit(UiEvent.ReleasePokemon)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            e.message?: "Couldn't release pokemon"
                        ))
                    }
                }
            }
            is PokemonDetailState.ReleasingProcess -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowReleaseDialog(
                        event.pokemon
                    ))
                }
            }
        }
    }

    fun calculateCatchPokemon() : Boolean {
        return true
    }

    private fun checkIsPokemonCapturedBefore(pokemonName: String)  {
        checkCapturePokemonJob?.cancel()
        checkCapturePokemonJob = repository.getPokemon(pokemonName).onEach {
            _isAlreadyCaptured.value = it != null
        }.launchIn(viewModelScope)
    }
}