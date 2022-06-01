package com.example.android.pokemonpokedex.presentation.pokemon_detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.pokemonpokedex.domain.repository.PokemonRepository
import com.example.android.pokemonpokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PokemonDetailScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    var state by mutableStateOf(PokemonDetailState())

    init {
        viewModelScope.launch {
            val colour = savedStateHandle.get<Int>("colour")
            val pokemonName = savedStateHandle.get<String>("name")
            pokemonName?.let {
                getPokemonByName(it)
            }
            colour?.let {
                state = state.copy(dominantColour = it)
            }
        }
    }

    private fun getPokemonByName(name: String){
        viewModelScope.launch {
            try {
                when (val result = repository.getPokemonByName(name)) {
                    is Resource.Success -> {
                        state = state.copy(
                            pokemon = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> { state = state.copy(error = result.message) }
                    is Resource.Loading -> {
                        state = state.copy(isLoading =  result.isLoading)
                    }
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}