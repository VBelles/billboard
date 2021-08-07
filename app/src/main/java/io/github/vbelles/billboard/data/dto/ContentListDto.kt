package io.github.vbelles.billboard.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentListDto(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<ContentDto>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int,
)
