package io.github.vbelles.billboard.data.repository.content

import io.github.vbelles.billboard.data.dto.ContentDto
import io.github.vbelles.billboard.data.dto.PagedContentsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ContentApiClient {

    @GET("{content_type}/{id}")
    suspend fun listContents(
        @Path("id") id: String,
        @Path("content_type") contentType: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): Response<PagedContentsDto>

    @GET("{content_type}/{id}")
    suspend fun findContent(
        @Path("id") id: Int,
        @Path("content_type") contentType: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("region") region: String? = null,
    ): Response<ContentDto>

}