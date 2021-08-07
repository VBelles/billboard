package io.github.vbelles.billboard.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StripDto(
    @SerialName("name") val name: String,
    @SerialName("source") val source: String,
)