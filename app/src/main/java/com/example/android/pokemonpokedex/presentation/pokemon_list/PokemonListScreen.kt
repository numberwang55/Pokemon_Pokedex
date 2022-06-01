package com.example.android.pokemonpokedex.presentation.pokemon_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.pokemonpokedex.R
import com.example.android.pokemonpokedex.data.mapper.toPokedexListEntry
import com.example.android.pokemonpokedex.presentation.destinations.PokemonDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun PokemonListScreen(
    navigator: DestinationsNavigator,
    viewModel: PokemonListScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val scrollState = rememberLazyGridState()
    val endReached = remember {
        derivedStateOf {
            val lastVisibleItem =
                scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
                    ?: return@derivedStateOf true
            lastVisibleItem.index == scrollState.layoutInfo.totalItemsCount - 1
        }
    }
    LaunchedEffect(key1 = endReached.value) {
        viewModel.loadNextPokemon()
    }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = state.nameQuery,
                onValueChange = { newQuery ->
                    viewModel.onEvent(
                        PokemonListEvent.OnSearchQueryChange(newQuery)
                    )
                },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White, CircleShape),
                placeholder = {
                    Text(text = "Search for a Pokemon...")
                },
                maxLines = 1,
                singleLine = true,
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = scrollState,
            ) {
                if (state.searchedPokemon != null) {
                    item {
                        PokedexEntry(entry = state.searchedPokemon.toPokedexListEntry(),
                            onPokemonClick = {colour, name ->
                                navigator.navigate(PokemonDetailScreenDestination(colour = colour, name = name.lowercase()))
                            }
                        )
                    }
                } else {
                    items(state.pokemon){ entry ->
                        PokedexEntry(entry = entry, onPokemonClick = { colour, name ->
                            navigator.navigate(PokemonDetailScreenDestination(colour = colour, name = name.lowercase()))
                        })
                    }
                }
            }
        }
    }
}
