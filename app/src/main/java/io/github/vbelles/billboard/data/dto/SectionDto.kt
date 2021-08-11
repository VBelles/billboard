package io.github.vbelles.billboard.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SectionDto(
    @SerialName("title") val title: String,
    @SerialName("header") val header: HeaderDto? = null,
    @SerialName("strips") val strips: List<StripDto> = emptyList(),
)