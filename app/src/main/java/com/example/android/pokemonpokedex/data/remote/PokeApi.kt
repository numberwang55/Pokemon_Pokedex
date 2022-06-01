package com.example.android.pokemonpokedex.data.remote

import com.example.android.pokemonpokedex.data.remote.dto.PokemonDto
import com.example.android.pokemonpokedex.data.remote.dto.PokemonListDto
import com.example.android.pokemonpokedex.util.Constants.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    //https://pokeapi.co/api/v2/pokemon
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = PAGE_SIZE,
        @Query("offset") offset: Int = 20
    ): PokemonListDto

    //https://pokeapi.co/api/v2/pokemon/pikachu
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonDto

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}