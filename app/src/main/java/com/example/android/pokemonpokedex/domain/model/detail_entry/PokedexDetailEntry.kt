package com.example.android.pokemonpokedex.domain.model.detail_entry

data class PokedexDetailEntry(
    val pokemonName: String,
    val imageUrl: String,
    val id: Int,
    val stats: List<PokedexDetailStats>,
    val height: Int,
    val weight: Int,
    val fightType: List<PokedexDetailTypes>
)
