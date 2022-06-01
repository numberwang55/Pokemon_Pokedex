package com.example.android.pokemonpokedex.data.paging

interface Paginator<Key, Item> {
    suspend fun loadNextPokemon()
    fun reset()
}