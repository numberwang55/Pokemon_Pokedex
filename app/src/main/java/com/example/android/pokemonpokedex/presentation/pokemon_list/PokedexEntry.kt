package com.example.android.pokemonpokedex.presentation.pokemon_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.android.pokemonpokedex.domain.model.list_entry.PokedexListEntry
import com.example.android.pokemonpokedex.presentation.ui.theme.RobotoCondensed

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    modifier: Modifier = Modifier,
    viewModel: PokemonListScreenViewModel = hiltViewModel(),
    onPokemonClick: (Int, String) -> Unit
) {
    val defaultDominantColour = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColour)
    }
    var isImageLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(6.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColour
                    )
                )
            )
            .clickable {
                onPokemonClick(dominantColor.toArgb(), entry.pokemonName)
            }
    ) {
        Column {
            if (isImageLoading)
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .scale(0.5f)
                        .align(CenterHorizontally)
                )
            val request = ImageRequest.Builder(LocalContext.current)
                .data(entry.imageUrl)
                .crossfade(true)
                .build()
            AsyncImage(
                model = request,
                contentDescription = "Pokemon Image",
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                onSuccess = {
                    val image = it.result.drawable
                    viewModel.calculateDominantColour(image) { colour ->
                        dominantColor = colour
                    }
                    isImageLoading = false
                }
            )
            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


