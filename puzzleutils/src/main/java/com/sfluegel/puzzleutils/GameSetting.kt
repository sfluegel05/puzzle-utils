package com.sfluegel.puzzleutils

/**
 * One selectable option within a [GameSetting].
 *
 * @param value The typed value this option represents.
 * @param label Short display name (e.g. "Mild", "Regular").
 * @param emoji Decorative emoji for this option (may be empty).
 */
data class SettingOption<T>(
    val value: T,
    val label: String,
    val emoji: String
) {
    /** Emoji and label joined with a space; gracefully handles empty emoji. */
    val displayName: String get() =
        listOfNotNull(emoji.ifEmpty { null }, label.ifEmpty { null }).joinToString(" ")
}

/**
 * A single game setting with a fixed, ordered list of options.
 *
 * Subclass this (typically as an `object`) to define a concrete setting.
 *
 * @param name        Human-readable name of the setting (e.g. "Difficulty").
 * @param options     All valid options, in display order.
 * @param defaultIndex Index into [options] for the default selection.
 */
open class GameSetting<T>(
    val name: String,
    val options: List<SettingOption<T>>,
    val defaultIndex: Int = 0
) {
    val default: SettingOption<T> get() = options[defaultIndex]

    fun optionFor(value: T): SettingOption<T> = options.first { it.value == value }
    fun label(value: T): String = optionFor(value).label
    fun emoji(value: T): String = optionFor(value).emoji
    fun displayName(value: T): String = optionFor(value).displayName
}
