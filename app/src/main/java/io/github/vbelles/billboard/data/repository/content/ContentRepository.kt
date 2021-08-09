package io.github.vbelles.billboard.data.repository.content

import io.github.vbelles.billboard.data.dto.ContentDto
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.PagedContents
import io.github.vbelles.billboard.data.resultOf

class ContentRepository(
    private val contentApiClient: ContentApiClient,
    private val apiKey: String,
    private val imageBaseUrl: String
) {

    suspend fun listContents(source: String): Result<List<Content>> {
        return resultOf {
            contentApiClient.listContents(source, apiKey, "en-US", 1)
        }.map { pagedContentsDto ->
            pagedContentsDto.results.map { dto -> dto.toContent() }
        }
    }

    suspend fun listPagedContents(source: String, page: Int): Result<PagedContents> {
        return resultOf {
            contentApiClient.listContents(source, apiKey, "en-US", page)
        }.map { pagedContentsDto ->
            PagedContents(
                contents = pagedContentsDto.results.map { dto -> dto.toContent() },
                nextPage = (pagedContentsDto.page + 1).takeIf { pagedContentsDto.totalPages > pagedContentsDto.page },
                page = pagedContentsDto.page,
            )
        }
    }

    private fun ContentDto.toContent() = Content(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        posterPath = imageBaseUrl + posterPath,
        genreIds = genreIds,
        title = title ?: name ?: "",
        originalTitle = originalTitle ?: originalName ?: "",
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        releaseDate = releaseDate,
        firstAirDate = firstAirDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}