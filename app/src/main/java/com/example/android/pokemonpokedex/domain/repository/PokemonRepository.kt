package com.example.android.pokemonpokedex.domain.repository

import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailEntry
import com.example.android.pokemonpokedex.domain.model.list_entry.PokedexListEntry
import com.example.android.pokemonpokedex.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(page: Int, pageSize: Int): Flow<Resource<List<PokedexListEntry>>>

    suspend fun getPokemonByName(name: String): Resource<PokedexDetailEntry>
}