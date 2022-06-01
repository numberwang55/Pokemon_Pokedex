package com.example.android.pokemonpokedex.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailEntry
import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailStats
import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailType
import com.example.android.pokemonpokedex.domain.model.detail_entry.PokedexDetailTypes
import com.example.android.pokemonpokedex.presentation.ui.theme.*
import java.util.*

fun parseTypeToColor(pokedexDetailTypes: PokedexDetailTypes): Color {
    return when(pokedexDetailTypes.type.name) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(pokedexDetailEntry: PokedexDetailStats): Color {
    return when(pokedexDetailEntry.stats.name) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(pokedexDetailEntry: PokedexDetailStats): String {
    return when(pokedexDetailEntry.stats.name) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}