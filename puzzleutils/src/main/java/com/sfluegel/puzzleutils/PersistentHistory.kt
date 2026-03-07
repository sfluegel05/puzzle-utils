package com.sfluegel.puzzleutils

import android.content.Context

/**
 * Persistent solve-history store backed by [SharedPreferences][android.content.SharedPreferences].
 *
 * Subclass this (typically as an `object`) and implement [serialize], [deserialize],
 * and [timestampOf] to persist records of any type [R].
 *
 * Call [init] once at app startup (e.g. in `Application.onCreate`) to load saved records,
 * then [add] to append and immediately persist each new record.
 *
 * @param prefsName  Name of the SharedPreferences file used for storage.
 */
abstract class PersistentHistory<R : Any>(private val prefsName: String) {
    private val _records = mutableListOf<R>()
    val records: List<R> get() = _records.toList()

    /** Converts a record to a storable string. Must be reversible by [deserialize]. */
    protected abstract fun serialize(record: R): String

    /** Parses a previously serialized string back to a record; returns null on failure. */
    protected abstract fun deserialize(s: String): R?

    /** Returns the timestamp (ms since epoch) used to sort records chronologically. */
    protected abstract fun timestampOf(record: R): Long

    /** Loads and sorts all previously persisted records. Call once at app startup. */
    fun init(context: Context) {
        val prefs  = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val stored = prefs.getStringSet("records", emptySet()) ?: emptySet()
        _records.clear()
        stored.mapNotNullTo(_records) { deserialize(it) }
        _records.sortBy { timestampOf(it) }
    }

    /** Appends a record to the in-memory list and immediately writes it to storage. */
    fun add(record: R, context: Context) {
        _records.add(record)
        val prefs  = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val stored = prefs.getStringSet("records", emptySet())?.toMutableSet() ?: mutableSetOf()
        stored.add(serialize(record))
        prefs.edit().putStringSet("records", stored).apply()
    }
}
