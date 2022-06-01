package com.example.android.pokemonpokedex.presentation.pokemon_detail

import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailEntry

data class PokemonDetailState(
    val pokemon: PokedexDetailEntry? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val dominantColour: Int? = null
)
