package com.example.android.pokemonpokedex.presentation.pokemon_list

import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailEntry
import com.example.android.pokemonpokedex.domain.model.list_entry.PokedexListEntry

data class PokemonListState(
    val pokemon: List<PokedexListEntry> = emptyList(),
    val searchedPokemon: PokedexDetailEntry? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val nameQuery: String = "",
    val page: Int = 0,
    val offset: Int = 20,
    val endReached: Boolean = false,
    val error: String? = null
)
