package io.github.vbelles.billboard.data.model

enum class ContentType(val id: String) {
    Movie("movie"), TvShow("tv");

    companion object {
        fun fromId(id: String) = values().find { it.id == id }
    }
}