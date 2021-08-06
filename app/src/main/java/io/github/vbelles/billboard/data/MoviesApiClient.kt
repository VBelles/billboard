package io.github.vbelles.billboard.data

import io.github.vbelles.billboard.data.dto.MoviesListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApiClient {

    suspend fun listNowPlaying(
        apiKey: String, language: String, page: Int, region: String? = null
    ): Response<MoviesListDto> = listMovies("now_playing", apiKey, language, page, region)

    suspend fun listPopular(
        apiKey: String, language: String, page: Int, region: String? = null
    ): Response<MoviesListDto> = listMovies("popular", apiKey, language, page, region)

    suspend fun listTopRated(
        apiKey: String, language: String, page: Int, region: String? = null
    ): Response<MoviesListDto> = listMovies("top_rated", apiKey, language, page, region)

    suspend fun listUpcoming(
        apiKey: String, language: String, page: Int, region: String? = null
    ): Response<MoviesListDto> = listMovies("upcoming", apiKey, language, page, region)

    @GET("/movie/{type}")
    suspend fun listMovies(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String?,
    ): Response<MoviesListDto>

}