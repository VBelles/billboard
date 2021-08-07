package io.github.vbelles.billboard.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SectionDto(
    @SerialName("name") val name: String,
    @SerialName("icon") val icon: String,
    @SerialName("sectionType") val sectionType: String,
    @SerialName("strips") val strips: List<StripDto> = emptyList(),
)