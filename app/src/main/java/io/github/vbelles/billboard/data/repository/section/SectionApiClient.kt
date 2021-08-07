package io.github.vbelles.billboard.data.repository.section

import io.github.vbelles.billboard.data.dto.SectionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface SectionApiClient {

    @GET
    suspend fun listSections(@Url endpoint: String): Response<List<SectionDto>>

}