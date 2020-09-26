package com.lucasrodrigues.pokemonshowcase.extensions

fun String.toId(): Int {
    return split("/").last { it.isNotEmpty() }.toInt()
}

fun String.toOffset(): Int {
    return split("?")
        .last()
        .split("&")
        .single { it.contains("offset") }
        .split("=")
        .last()
        .toInt()
}

fun String.normalizeToDisplay(): String {
    return this.trim()
        .replace("-", " ")
        .capitalize()
}

fun String.normalizeToQuery(): String {
    return this.trim()
        .toLowerCase()
        .replace(" ", "-")
        .replace(".", "")
        .replace("'", "")
}