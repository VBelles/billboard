package io.github.vbelles.billboard.data.repository.section

import io.github.vbelles.billboard.data.dto.SectionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface SectionApiClient {

    @GET("{id}.json")
    suspend fun getSection(@Path("id") id: String): Response<SectionDto>

}