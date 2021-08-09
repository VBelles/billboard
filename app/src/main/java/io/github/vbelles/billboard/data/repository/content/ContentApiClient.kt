package io.github.vbelles.billboard.data.repository.content

import io.github.vbelles.billboard.data.dto.PagedContentsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ContentApiClient {

    @GET
    suspend fun listContents(
        @Url source: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): Response<PagedContentsDto>

}