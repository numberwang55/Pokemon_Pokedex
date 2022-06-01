package com.example.android.pokemonpokedex.presentation.pokemon_list

sealed class PokemonListEvent {
    data class OnSearchQueryChange(val query: String): PokemonListEvent()
}
