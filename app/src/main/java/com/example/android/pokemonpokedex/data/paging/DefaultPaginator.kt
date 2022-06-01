package com.example.android.pokemonpokedex.data.paging

import com.example.android.pokemonpokedex.util.Resource
import kotlinx.coroutines.flow.Flow

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Flow<Resource<List<Item>>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextPokemon() {
        if (isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        result.collect {
            currentKey = it.data?.let { it1 -> getNextKey(it1) }!!
            onSuccess(it.data, currentKey)
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentKey = initialKey
    }
}