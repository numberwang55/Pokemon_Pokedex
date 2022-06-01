package com.example.android.pokemonpokedex.presentation.pokemon_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.android.pokemonpokedex.data.paging.DefaultPaginator
import com.example.android.pokemonpokedex.domain.repository.PokemonRepository
import com.example.android.pokemonpokedex.util.Constants.PAGE_SIZE
import com.example.android.pokemonpokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var state by mutableStateOf(PokemonListState())
    private var searchJob: Job? = null

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getPokemonList(nextPage, PAGE_SIZE)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                pokemon = state.pokemon + items,
                isLoading = false,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextPokemon()
    }

    fun loadNextPokemon() {
        viewModelScope.launch {
            paginator.loadNextPokemon()
        }
    }

    fun onEvent(event: PokemonListEvent) {
        when (event) {
            is PokemonListEvent.OnSearchQueryChange -> {
                state = state.copy(nameQuery = event.query.trim().lowercase())
                if (state.nameQuery.isNotEmpty()) {
                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        delay(1000L)
                        searchPokemon()
                    }
                }
                if (state.nameQuery.isEmpty()) {
                    searchJob?.cancel()
                    state = state.copy(searchedPokemon = null)
                }
            }
        }
    }

    private fun searchPokemon(name: String = state.nameQuery) {
        viewModelScope.launch {
            val result = repository.getPokemonByName(name)
            when(result) {
                is Resource.Success -> {
                    state = state.copy(searchedPokemon = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }
    }

    fun calculateDominantColour(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let {
                onFinish(Color(it))
            }
        }
    }

}



