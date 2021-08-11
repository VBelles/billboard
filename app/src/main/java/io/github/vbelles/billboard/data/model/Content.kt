package io.github.vbelles.billboard.data.model

data class Content(
    val id: Int,
    val type: ContentType,
    val adult: Boolean,
    val backdropPath: String?,
    val posterPath: String?,
    val genreIds: List<Int>,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String?,
    val firstAirDate: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)