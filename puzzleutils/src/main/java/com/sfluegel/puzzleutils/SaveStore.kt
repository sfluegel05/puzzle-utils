package com.sfluegel.puzzleutils

/**
 * In-memory store for in-progress game saves, keyed by a configuration identifier.
 *
 * Subclass this (typically as an `object`) to add game-specific fields alongside
 * the inherited [put], [get], [getLastSaved], and [clear] operations.
 *
 * @param K     Key type (e.g. grid size as [Int]).
 * @param V     Saved-game type.
 * @param keyOf Extracts the store key from a value. Called on every [put].
 */
open class SaveStore<K, V>(private val keyOf: (V) -> K) {
    private val saves   = HashMap<K, V>()
    private var lastKey: K? = null

    fun put(value: V) {
        val key = keyOf(value)
        saves[key] = value
        lastKey = key
    }

    fun get(key: K): V? = saves[key]

    fun getLastSaved(): V? = lastKey?.let { saves[it] }

    fun clear(key: K) {
        saves.remove(key)
        if (lastKey == key) lastKey = null
    }
}
