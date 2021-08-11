package io.github.vbelles.billboard.data.repository.content

import io.github.vbelles.billboard.data.dto.ContentDto
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.data.model.PagedContents
import io.github.vbelles.billboard.data.resultOf

class ContentRepository(
    private val contentApiClient: ContentApiClient,
    private val apiKey: String,
    private val imageBaseUrl: String
) {

    suspend fun listContents(source: String, contentType: ContentType): Result<List<Content>> {
        return resultOf {
            contentApiClient.listContents(source, contentType.id, apiKey, "en-US", 1)
        }.map { pagedContentsDto ->
            pagedContentsDto.results.map { dto -> dto.toContent(contentType) }
        }
    }

    suspend fun listPagedContents(source: String, contentType: ContentType, page: Int): Result<PagedContents> {
        return resultOf {
            contentApiClient.listContents(source, contentType.id, apiKey, "en-US", page)
        }.map { pagedContentsDto ->
            PagedContents(
                contents = pagedContentsDto.results.map { dto -> dto.toContent(contentType) },
                nextPage = (pagedContentsDto.page + 1).takeIf { pagedContentsDto.totalPages > pagedContentsDto.page },
                page = pagedContentsDto.page,
            )
        }
    }

    suspend fun findContent(id: Int, contentType: ContentType): Result<Content> {
        return resultOf {
            contentApiClient.findContent(id, contentType.id, apiKey, "en-US")
        }.map { contentDto -> contentDto.toContent(contentType) }
    }

    private fun ContentDto.toContent(contentType: ContentType) = Content(
        id = id,
        type = contentType,
        adult = adult,
        backdropPath = imageBaseUrl + backdropPath,
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