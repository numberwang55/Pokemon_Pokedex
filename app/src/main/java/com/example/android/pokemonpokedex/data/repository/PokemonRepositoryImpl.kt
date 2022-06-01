package com.example.android.pokemonpokedex.data.repository

import com.example.android.pokemonpokedex.data.mapper.toListOfPokedexListEntry
import com.example.android.pokemonpokedex.data.mapper.toPokedexDetailEntry
import com.example.android.pokemonpokedex.data.remote.PokeApi
import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailEntry
import com.example.android.pokemonpokedex.domain.model.list_entry.PokedexListEntry
import com.example.android.pokemonpokedex.domain.repository.PokemonRepository
import com.example.android.pokemonpokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
): PokemonRepository {

    override suspend fun getPokemonList(
        page: Int,
        pageSize: Int
    ): Flow<Resource<List<PokedexListEntry>>> = flow {
        try {
            val response = api.getPokemonList(offset = page * pageSize).toListOfPokedexListEntry()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data IOException"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data HttpException"))
        }
    }

    override suspend fun getPokemonByName(name: String): Resource<PokedexDetailEntry> {
        return try {
            val response = api.getPokemonByName(name).toPokedexDetailEntry()
            Resource.Success(data = response)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data IOException")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load data HttpException ${e.message}")
        }
    }

}
