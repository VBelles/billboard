package io.github.vbelles.billboard.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StripDto(
    @SerialName("title") val title: String,
    @SerialName("type") val contentType: String,
    @SerialName("source") val source: String,
)