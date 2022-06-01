package com.example.android.pokemonpokedex.data.mapper

import com.example.android.pokemonpokedex.data.remote.dto.*
import com.example.android.pokemonpokedex.domain.model.detail_entry.*
import com.example.android.pokemonpokedex.domain.model.list_entry.PokedexListEntry
import java.util.*

fun PokemonListDto.toListOfPokedexListEntry(): List<PokedexListEntry> {
    return results.mapIndexed { index, entry ->
        val number = if(entry.url.endsWith("/")) {
            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            entry.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
    }
}

fun PokemonDto.toPokedexDetailEntry(): PokedexDetailEntry {
    val number = if (species.url.endsWith("/") && species.url.isNotBlank()) {
        species.url.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        species.url.takeLastWhile { it.isDigit() }
    }
    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
    return PokedexDetailEntry(
        pokemonName = name,
        imageUrl = url,
        id = number.toInt(),
//        hp = stats.first().base_stat,
//        attack = stats.first{
//            it.stat.name == "attack"
//        }.base_stat,
//        defence = stats.first{
//            it.stat.name == "defense"
//        }.base_stat,
//        specialAttack = stats.first {
//            it.stat.name == "special-attack"
//        }.base_stat,
//        specialDefence = stats.first {
//            it.stat.name == "special-defense"
//        }.base_stat,
//        speed = stats.first {
//            it.stat.name == "speed"
//        }.base_stat,
        stats = stats.map { it.toPokedexDetailStats() },
        height = height,
        weight = weight,
//        elementType = types.first().type.name,
//        fightType = types.last().type.name
        fightType = types.map { it.toPokedexDetailTypes() }
    )
}

fun PokedexDetailEntry.toPokedexListEntry(): PokedexListEntry {
    return PokedexListEntry(
        pokemonName = pokemonName,
        imageUrl = imageUrl,
        number = id
    )
}

fun Stat.toPokedexDetailStats(): PokedexDetailStats {
    return PokedexDetailStats(
        baseStat = base_stat,
        effort = effort,
        stats = stat.toPokedexDetailStat()
    )
}

fun StatX.toPokedexDetailStat(): PokedexDetailStat {
    return PokedexDetailStat(
        name = name,
        url = url
    )
}

fun Type.toPokedexDetailTypes(): PokedexDetailTypes {
    return PokedexDetailTypes(
        slot = slot,
        type = type.toPokedexDetailType()
    )
}

fun TypeX.toPokedexDetailType(): PokedexDetailType {
    return PokedexDetailType(
        name = name,
        url = url
    )
}